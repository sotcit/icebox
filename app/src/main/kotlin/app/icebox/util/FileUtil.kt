package app.icebox.util

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import app.icebox.MainActivity
import java.io.File
import java.io.FileOutputStream

@ExperimentalFoundationApi
object FileUtil {
    private val app = MainActivity.app
    private val packageManager = app.packageManager

    /**
     * 删除指定目录或文件
     * @param fileOrDir 目录或文件
     * @return 目录或文件不存在返回 true
     */
    fun deleteFileOrDir(fileOrDir: File): Boolean {
        return if (!fileOrDir.exists()) {
            !fileOrDir.exists()
        } else {
            if (fileOrDir.isDirectory) {
                fileOrDir.listFiles().run {
                    if (this != null) {
                        for (file in this) {
                            if (file.isFile) {
                                file.delete()
                            }
                        }
                    }
                }
                fileOrDir.delete()
                !fileOrDir.exists()
            } else {
                fileOrDir.delete()
                !fileOrDir.exists()
            }
        }
    }

    /**
     *创建指定目录
     * @param dir 目录
     * @return 目录存在返回 true
     */
    fun createDir(dir: File): Boolean {
        return if (dir.exists()) {
            dir.exists()
        } else {
            dir.mkdirs()
            dir.exists()
        }
    }

    fun initFileDir(fileDir: File) {
        deleteFileOrDir(fileDir)
        createDir(fileDir)
    }

    fun getIconDir(): File {
        return File("${app.filesDir.absolutePath}/icon")
    }

    /**
     *  Resources 中保存文件到本地目录
     */
    fun copyFile(resources: Resources, identifier: Int, file: File) {
        val inputStream = resources.openRawResource(identifier)
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(inputStream.readBytes())
        fileOutputStream.close()
    }
}