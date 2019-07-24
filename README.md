## replace-maven-plugin

#### 1. 背景

背景：swift引擎fr版本和开源版本维护两套代码（就包名不同）太麻烦，维护成本高

要求：代码启动时用原包名jar，但是打包fr版本时，要替换成com.fr.third的包名

预期：开源版本和fr版本在各自的环境里都能运行良好

#### 2. 基本介绍

在项目代码的[生命周期](https://kms.finedevelop.com/pages/viewpage.action?pageId=73533826&src=contextnavpagetreemode)中，在编译之前将java源文件通过流的读取和扫描实现替换，也可以通过pom.xml参数实现

自定义的内容的替换。下面是替换的核心代码：

```java
private void replace(String path, String origin, String targin) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new 		   FileInputStream(path)));
        //内存流
        CharArrayWriter tempStream = new CharArrayWriter();
        String line;
        while ((line = reader.readLine()) != null) {
            tempStream.write(line.replaceAll(origin, targin) + "\n");
        }
        reader.close();
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
    }
```

反之，替换回来只需要替换修改origin和target的位置即可

#### 3. 使用方法

下面是插件的使用示例：

```xml
			<plugin>
                    <groupId>com.fr</groupId>
                    <artifactId>replace-maven-plugin</artifactId>
                    <version>1.0-SNAPSHOT</version>
                    <configuration>
                        <origins>
                            <origin>words</origin>
                        </origins>
                        <targets>
                            <target>word</target>
                        </targets>
                    </configuration>
                    <executions>
                        <execution>
                            <id>replace</id>
                            <phase>generate-sources</phase>
                            <goals>
                                <goal>replace</goal>
                            </goals>
                        </execution>
                        <execution>
                            <id>back</id>
                            <phase>package</phase>
                            <goals>
                                <goal>back</goal>
                            </goals>
                        </execution>
                    </executions>
            </plugin>
```

示例中，在两个生命周期中分别使用的插件的两个goal，一个用于replace，一个用于back

同时参数的设置中，使用两个数组传递参数，分别在origins和targets标签

