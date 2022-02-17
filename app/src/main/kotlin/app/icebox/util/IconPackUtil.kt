package app.icebox.util

import android.content.res.Resources
import android.content.res.XmlResourceParser
import androidx.compose.foundation.ExperimentalFoundationApi
import app.icebox.MainActivity

@ExperimentalFoundationApi
object IconPackUtil {
    private val app = MainActivity.app
    private val packageManager = app.packageManager

    /**
     * 通过 packageName 获取 application's Resources
     * @param packageName 包名
     * @return 应用资源
     */
    fun getAppResources(packageName: String): Resources {
        return packageManager.getResourcesForApplication(packageName)
    }

    /**
     * 获取 resource ID
     * @param resources 应用资源
     * @param name 资源名称
     * @param defType 资源类型
     * @param defPackage 包名
     */
    fun getIdentifier(
        resources: Resources,
        name: String,
        defType: String,
        defPackage: String
    ): Int {
        return resources.getIdentifier(name, defType, defPackage)
    }

    /**
     * 获取一个 XML 解析器对象
     * @param resources 应用资源
     * @param identifier 资源 ID
     */
    fun getXmlResourceParser(resources: Resources, identifier: Int): XmlResourceParser {
        return resources.getXml(identifier)
    }

    /**
     * 解析 XML
     * @param xmlResourceParser XML 解析器对象
     */
    fun parseXml(xmlResourceParser: XmlResourceParser) {

    }


}