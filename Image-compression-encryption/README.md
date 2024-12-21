# Image-compression-encryption

本项目旨在实现图像的压缩和加密算法，提供一个基本的框架来处理图像的存储和安全性。下面是项目的概述和基本实现步骤。

### 功能

1. **图像压缩**：使用算法（如哈夫曼编码）减少图像文件的大小。
2. **图像加密**：使用简单的加密算法（如 AES 或自定义算法）保护图像数据，确保其安全性。

### 完成度

- [x] 图像的哈夫曼压缩（opencv）
- [x] 图像的哈夫曼压缩（java原生）
- [x] 图像简单加密

### 启动项目

StartByCV : opencv版的启动

StartByJava : java原生的启动



## 相关依赖

若想运行 OpenCV 版本的代码，请预先安装 OpenCV，并完成路径配置。

### OpenCV 安装与配置

1. **下载 OpenCV**: 从 [OpenCV 官方网站](https://opencv.org/releases/) 下载适合你操作系统的版本。
2. **安装 OpenCV**: 解压下载的文件，并根据操作系统的要求设置环境变量。
3. **在 Java 项目中添加 OpenCV JAR**: 将 OpenCV 的 Java JAR 文件（通常在 `opencv/build/java` 目录下）添加到项目的类路径中。
4. **加载 OpenCV 库**: 在代码中使用 `System.loadLibrary(Core.NATIVE_LIBRARY_NAME);` 加载 OpenCV 本地库。