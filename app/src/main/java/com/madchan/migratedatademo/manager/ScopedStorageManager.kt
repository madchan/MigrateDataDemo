package com.madchan.migratedatademo.manager

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.madchan.migratedatademo.util.LogUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*

/**
 * 分区存储管理类
 * <p>
 * 为了让用户能更好地管理自己的文件并减少混乱，Android 10 引入了名为分区存储的隐私权变更，
 * 即以 Android 10 及更高版本为目标平台的应用，在默认情况下，只能看到本应用专有的目录
 * （/sdcard/Android/data/{package_name}/）以及特定类型的媒体（照片、视频、音频等）。
 * 如果应用尝试打开此目录之外的文件，则会发生错误(即使拥有 READ_EXTERNAL_STORAGE 权限)。
 */
class ScopedStorageManager {

    companion object {
        val TAG = ScopedStorageManager::class.simpleName

        /** 标准目录-应用数据 */
        private const val DIRECTORY_DATA = "Data"

        /**
         * 获取应用专属内部存储空间的持久性文件目录
         * <p>
         * 该目录适合存储只有应用本身才能访问的敏感数据。
         * 如果应用的功能取决于某些文件，应改为将这些文件存储在内部存储空间中。
         * <p>
         * 系统会阻止其他应用访问这些位置，并且在 Android 10（API 级别 29）及更高版本中，系统会对这些位置进行加密。
         * 但是，请注意，这些目录的空间通常比较小。在将应用专属文件写入内部存储空间之前，应用应查询设备上的可用空间。
         * @param context   上下文
         * @param dirName   子目录名称
         */
        fun getInternalStorageDir(context: Context, dirName: String): File {
            val file = File(context.filesDir, dirName)
            if (!file?.mkdirs()) {
                LogUtil.e("应用专属内部存储空间的持久性文件目录创建失败")
            }
            return file
        }

        /**
         * 获取应用专属内部存储空间的缓存文件目录
         * <p>
         * 注意：当设备的内部存储空间不足时，Android 可能会删除这些缓存文件以回收空间。因此，请在读取前检查缓存文件是否存在。
         * @param context   上下文
         * @param dirName   子目录名称
         */
        fun getInternalCacheDir(context: Context, dirName: String): File {
            val file = File(context.cacheDir, dirName)
            if (!file?.mkdirs()) {
                LogUtil.e("应用专属内部存储空间的缓存文件目录创建失败")
            }
            return file
        }

        /**
         * 获取应用专属外部存储空间的持久性文件目录
         * <p>
         * 如果内部存储空间不足以存储应用专属文件，请考虑改为使用外部存储空间。
         * 系统会在外部存储空间中提供目录，应用可以在该存储空间中整理仅在应用内对用户有价值的文件。
         * <p>
         * 我们无法保证可以访问这些目录中的文件，例如从设备中取出可移除的 SD 卡后，就无法访问其中的文件。
         * <p>
         * 启用分区存储后，应用将无法访问属于其他应用的应用专属目录。
         * @param context   上下文
         * @param type      预定义子目录类型，可确保系统正确处理文件
         * @param dirName   子目录名称
         */
        fun getExternalStorageDir(context: Context, type: String?, dirName: String): File {
            val file = File(context.getExternalFilesDir(type?: DIRECTORY_DATA), dirName)
            if (!file?.mkdirs()) {
//                LogUtil.e("应用专属外部存储空间的持久性文件目录创建失败：$dirName")
            }
            return file
        }

        /**
         * 获取应用专属外部存储空间的缓存文件目录
         * @param context   上下文
         * @param dirName   子目录名称
         */
        fun getExternalCacheFir(context: Context, dirName: String): File {
            val file = File(context.externalCacheDir, dirName)
            if (!file?.mkdirs()) {
//                LogUtil.e("应用专属外部存储空间的缓存文件目录创建失败")
            }
            return file
        }

        /**
         * 查询设备上的可用空间
         * <p>
         * 如果您事先知道要存储的数据量，您可以通过调用 getAllocatableBytes() 查出设备可以为应用提供多少空间。
         * getAllocatableBytes() 的返回值可能大于设备上的当前可用空间量。这是因为系统已识别出可以从其他应用的缓存目录中移除的文件。
         * <p>
         * 如果有足够的空间保存您的应用数据，请调用 allocateBytes() 声明空间。
         * 否则，应用可以调用包含 ACTION_MANAGE_STORAGE 操作的 intent。此 intent 会向用户显示提示，
         * 要求他们在设备上选择要移除的文件，以便您的应用拥有所需空间。如果需要，此提示可以显示设备上的可用空间量。
         */
        @RequiresApi(Build.VERSION_CODES.O)
        fun queryAllocatableStorage(context: Context, storageDir: File, numByteNeed: Long) {
            val storageManager: StorageManager = context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
            val internalDirUuid: UUID = storageManager.getUuidForPath(storageDir)
            val availableBytes: Long =
                    storageManager.getAllocatableBytes(internalDirUuid)
            if (availableBytes >= numByteNeed) {
                storageManager.allocateBytes(
                        internalDirUuid, numByteNeed)
            } else {
                val storageIntent = Intent().apply {
                    action = ACTION_MANAGE_STORAGE
                }
                // 向用户显示提示，要求他们选择要删除的文件。
            }

        }

        /**
         * 检查包含外部存储是否可用于读写。
         * <p>
         * 由于外部存储空间位于用户可能能够移除的物理卷上，因此在尝试从外部存储空间读取
         * 应用专属数据或将应用专属数据写入外部存储空间之前，请验证该卷是否可访问。
         */
        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 检查包含外部存储是否至少可读取。
         * <p>
         * 由于外部存储空间位于用户可能能够移除的物理卷上，因此在尝试从外部存储空间读取
         * 应用专属数据或将应用专属数据写入外部存储空间之前，请验证该卷是否可访问。
         */
        fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() in
                    setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
        }

        /**
         * 获取主外部存储卷
         * <p>
         * 有时，分配内部存储分区作为外部存储空间的设备也会提供 SD 卡插槽。
         * 这意味着设备具有多个可能包含外部存储空间的物理卷，因此您需要选择用于应用专属存储空间的物理卷。
         * <p>
         * 如代码段中所示，返回数组中的第一个元素被视为主外部存储卷。除非该卷已满或不可用，否则请使用该卷。
         * @param applicationContext Application的上下文
         */
        fun getPrimaryExternalStorage(applicationContext : Context): File{
            val externalStorageVolumes: Array<out File> =
                    ContextCompat.getExternalFilesDirs(applicationContext, null)
            return externalStorageVolumes[0]
        }

        /**
         * 从旧版存储位置迁移现有文件
         * <p>
         * 1.检查应用的工作文件是否位于 /sdcard/ 目录或其任何子目录中。
         * 2.将任何私有应用文件从 /sdcard/ 下的当前位置移至 getExternalFilesDir() 方法所返回的目录。
         * 3.将任何共享的非媒体文件从 /sdcard/ 下的当前位置移至 Downloads/ 目录的应用专用子目录。
         * 4.从 /sdcard/ 目录中移除应用的旧存储目录。
         * @param dirMap 目录Map
         * @param listener 迁移进度监听器
         */
        fun migrateExistingFilesFromLegacyStorageDir(dirMap : Map<File, File?>, listener: ProgressListener) {
            Observable.create(ObservableOnSubscribe<Int> { emitter ->
                var totalSize = 0L
                // 计算需要迁移的总文件大小
                for((src, destDir) in dirMap.entries){
                    if(!src.exists()){
                        LogUtil.w("源文件或目录[${src.name}]不存在，不计入统计")
                        continue
                    }

                    if(destDir == null || !destDir.exists()){
                        LogUtil.w("目标目录[(${destDir?.name}]为空或不存在，不计入统计")
                        continue
                    }

                    if(!destDir.isDirectory) {
                        LogUtil.w("destDir[${destDir?.name}]非目录，不计入统计")
                        continue
                    }

                    totalSize += FileUtils.sizeOf(src)
                }
                emitter.onNext(0)   // 迁移开始

                LogUtil.d("totalSize = $totalSize")

                var migratedSize = 0L   // 已迁移的文件大小
                for ((src, destSir) in dirMap.entries) {
                    if(!src.exists()) {
                        LogUtil.w("源文件或目录[${src.name}]不存在，不执行迁移")
                        continue
                    }

                    if(src.isDirectory) {
                        for (file in src.listFiles()){
                            destSir?.let {
                                if(file.isDirectory){
                                    FileUtils.copyDirectoryToDirectory(file, destSir)
                                    LogUtil.d("迁移目录[${file.name}]至目录[${destSir.name}]...")
                                } else {
                                    FileUtils.copyFileToDirectory(file, destSir)
                                    LogUtil.d("迁移文件[${file.name}]至目录[${destSir.name}]...")
                                }

                                migratedSize += FileUtils.sizeOf(file)
                                LogUtil.d("migratedSize = $migratedSize")

                                val progress = (migratedSize * 100 / totalSize.toFloat()).toInt();
                                LogUtil.d("progress = $progress")

                                emitter.onNext(progress)    // 回调迁移进度

                                Thread.sleep(200)
                            }
                        }
                    } else {
                        destSir?.let {
                            FileUtils.copyFileToDirectory(src, destSir)
                            LogUtil.d("迁移文件[${src.name}]至目录[${destSir.name}]...")

                            migratedSize += FileUtils.sizeOf(src)
                            LogUtil.d("migratedSize = $migratedSize")

                            val progress = (migratedSize * 100 / totalSize.toFloat()).toInt();
                            LogUtil.d("progress = $progress")

                            emitter.onNext(progress)
                        }
                    }

                    LogUtil.d("迁移完成，删除文件或目录:[${src.name}]")
                    FileUtils.deleteQuietly(src)
                }
                emitter.onNext(100) // 迁移完成
            })
                    .distinct()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        when(it) {
                            0 -> listener.onStart()
                            100 -> listener.onFinish()
                            else -> listener.onProgress(it.toLong())
                        }
                    }, {
                        t -> LogUtil.e("从旧版存储位置迁移现有文件出错：${t.message}")
                        t.printStackTrace()
                        listener.onError()
                    })
        }

        /**
         * 快速删除文件，不会抛出异常
         * @set 待删除的文件集合
         */
        fun deleteFileQuietly(set : Set<File>) {
            for(file in set){
                if(!file.exists()) continue
                FileUtils.deleteQuietly(file)
            }
        }

        /**
         * 计算多个文件总大小
         * @param set 待计算大小的文件集合
         * @param listener 计算进度监听器
         */
        fun calculateFilesTotalSize(set : Set<File>, listener: ProgressListener?) {
            Observable.create(ObservableOnSubscribe<Long> { emitter ->
                var totalSize = 0L
                emitter.onNext(0)

                for(file in set){
                    if(!file.exists()) continue
                    totalSize += FileUtils.sizeOf(file)
                    emitter.onNext(totalSize)
                }

                listener?.onFinish()
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        when(it) {
                            0L -> listener?.onStart()
                            else -> listener?.onProgress(it.toLong())
                        }
                    }, {
                        t -> LogUtil.e("计算多个文件总大小出错：${t.message}")
                        t.printStackTrace()
                        listener?.onError()
                    })
        }
    }


    abstract class ProgressListener {
        abstract fun onStart()
        abstract fun onProgress(progress: Long)
        abstract fun onFinish()
        abstract fun onError()
    }
}