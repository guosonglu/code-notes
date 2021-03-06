---
layout: default
title: 结构型模式（Structural patterns）
nav_order: 4
parent: 设计模式（Design Pattern）
---

# 结构型模式概述

将类或对象组织在一起形成更加强大的结构

- `类结构型模式`：由多个类可以组合成一个更大的系统，一般只存在继承和实现关系
- `对象结构型模式`：类和对象的组合，通过关联关系在一个类中定义另一个类的实例对象，然后通过该对象调用相应的方法

# 适配器模式（Adapter）

## 别名

包装器（Wrapper）模式

## 模式分类

结构型模式

既可以作为 `类模式`，也可以作为 `对象模式`

## 模式概述

将一个类的接口转换成客户希望的另一个接口。适配器模式让那些接口不兼容的类可以一起工作

## 模式结构与实现

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/2cc49c5e2612df2547699803e2270fd3)

适配器模式包括 `类适配`器和 `对象适配器`：

- `类适配器`：`适配器`与 `适配者`之间是继承或实现关系
- `对象适配器`：`适配器`与 `适配者`之间是关联关系

组成成分：

- `Target(目标抽象类)`：定义客户端所需要的接口。如果是 `类适配器`模式，由于Java中不能多继承，因此 `目标抽象类`只能是接口
- `Adapter(适配器类)`：将 `Adaptee(适配者类)`与 `Target(目标抽象类)`进行关联与适配
- `Adaptee(适配者类)`：被适配的对象。这个类中的方法实现具体功能，但可能 `目标抽象类`的接口不匹配,因此需要适配器类进行匹配

## 实例代码

> 某公司开发一款儿童汽车，在移动过程中伴随着灯光闪烁和声音提示。该公司在以往产品中已经实现了警灯闪烁和警笛声的程序。
> 为了重用先前的代码并且使汽车控制软件具有更好的灵活性和拓展性，使用适配器模式设计该玩具汽车控制软件。

```java
/**
 * 汽车控制类，充当目标抽象类
 *
 * @author 10545
 * @date 2022/5/2 17:07
 */
public abstract class CarController {
    public void move() {
        System.out.println("玩具汽车移动！");
    }

    public abstract void phonate();

    public abstract void twinkle();
}

/**
 * 警笛声，充当适配者
 *
 * @author 10545
 * @date 2022/5/2 17:43
 */
public class PoliceSound {
    public void alarmSound() {
        System.out.println("发出警笛声");
    }
}

/**
 * 警灯类，充当适配者
 *
 * @author 10545
 * @date 2022/5/2 17:53
 */
public class PoliceLamp {
    public void alarmLamp() {
        System.out.println("呈现警灯闪烁");
    }
}

/**
 * 警车适配器，充当适配器
 *
 * @author 10545
 * @date 2022/5/2 20:49
 */
public class PoliceCarAdapter extends CarController {
    private PoliceSound sound;
    private PoliceLamp lamp;

    /**
     * 适配器构造
     */
    public PoliceCarAdapter() {
        sound = new PoliceSound();
        lamp = new PoliceLamp();
    }


    @Override
    public void phonate() {
        //调用适配者类PoliceSound的方法
        sound.alarmSound();
    }

    @Override
    public void twinkle() {
        //调用适配者类PoliceLamp的方法
        lamp.alarmLamp();
    }
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<config>
    <className>cn.com.lgs.adapter_pattern.PoliceCarAdapter</className>
</config>
```

```java
/**
 * 客户端测试类
 *
 * @author 10545
 * @date 2022/5/2 21:12
 */
public class Demo {
  public static void main(String[] args) {
    CarController car;
    car = (CarController) XMLUtil.getBean("_java/design_patterns/src/main/java/com/luguosong/_04_structural/_01_adapter_pattern/config.xml").get(0);
    car.move();
    car.phonate();
    car.twinkle();
  }
}
```

```text
玩具汽车移动！
发出警笛声
呈现警灯闪烁
```

## 模式拓展

### 缺省适配器模式

当不需要实现一个接口所提供的所有方法时，可先设计一个抽象类实现该接口，并为接口中每个方法提供一个默认实现（空方法），
那么该抽象类的子类可以选择性地覆盖父类的某些方法来实现需求，它适用于不想使用一个接口中的所有方法的情况，
又称为单接口适配器模式。

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220502223530.png)

### 双向适配器

如果在适配器中同时包含对 `目标类`和 `适配者类`的 `引用`，适配者可以通过它调用目标类中的方法，
目标类也可以通过它调用适配者类中的方法，那么该适配器就是一个双向适配器。

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220502224133.png)

## 效果

- 优点
    - 将 `目标类`和 `适配者类`解耦，通过引入一个适配器类来重用现有适配者类，无需修改原有结构
    - 将业务的具体实现封装在 `适配者类`中,对于客户端而言是透明的，提高了复用性。同一个 `适配者类`可以在多个不同的系统中复用。
    - 可以通过配置文件修改 `适配器类`，满足开闭原则，拓展性好。
    - `类适配器`是 `适配者类`的子类,因此可以在 `适配器类`中置换一些适配者的方法，使得适配器的灵活性更强。
    - `对象适配器`可以把多个不同的适配者适配到同一目标
    - `对象适配器`可以适配 `适配者类`的子类。
- 缺点
    - `类适配器`对于不支持多继承的语言，一次只能适配一个适配者类。
    - `类适配器`模式下，`适配者类`不能为最终类，例如Java中不能为final。
    - `类适配器`模式下,`目标抽象类`只能为接口，不能为类，有一定的局限性。
    - 在 `对象适配器`模式下,想要对 `适配者类`中的方法进行修改，需要做一个 `适配者类的子类`，在子类中进行方法的置换。然后再把 `适配者类的子类`当成真正的适配者进行适配。实现过程较为复杂

## 模式适用性

- 系统需要使用一些现有的类，而这些类的接口（例如方法名）不符合系统的需要，甚至没有这些类的源代码。
- 想创建一个可以重复使用的类，用于和一些彼此之间没有太大关联的类一起工作。

# 桥接模式（Bridge）

## 模式分类

结构型模式

对象模式

## 模式概述

将抽象部分与它的实现部分解耦，使得两者都能够独立变化

## 模式结构与实现

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220504175917.png)

- `Abstraction(抽象类)`:用于定义抽象类的接口，它一般是抽象类而不是接口，其中定义了一个 `Implementor（实现类接口）类型的对象`并可以维护该对象，
  它与Implementor之间具有关联关系，它既可以包含抽象业务方法，也可以包含具体业务方法。
- `RefinedAbstraction(扩充抽象类)`：扩充由Abstraction定义的接口，通常情况下它不再是抽象类而是具体类，它实现了在Abstraction中声明的抽象业务方法，
  在RefinedAbstraction中可以调用在Implementor中定义的业务方法。
- `Implementor(实现类接口)`:定义实现类的接口，这个接口不一定要与Abstraction的接口完全一致，事实上这两个接口可以完全不同，一般而言，Implementor接口仅提供基本操作，
  而Abstraction定义的接口可能会做更多更复杂的操作。Implementor接口对这些基本操作进行了声明，而具体实现交给其子类。通过关联关系，在Abstraction中不仅拥有自己的方法，
  还可以调用到Implementor中定义的方法，使用关联关系来替代继承关系。
- `ConcreteImplementor(具体实现类)`:具体实现Implementor接口，在不同的ConcreteImplementor中提供基本操作的不同实现，在程序运行时，
  ConcreteImplementor对象将替换其父类对象，提供给抽象类具体的业务操作方法。

## 实例代码

> Sunny软件公司欲开发一个跨平台图像浏览系统，要求该系统能够显示BMP、JPG、GIF、PNG等多种格式的文件，并且能够在Windows、Linux、Unix等多个操作系统上运行。
> 系统首先将各种格式的文件解析为像素矩阵(Matrix)，然后将像素矩阵显示在屏幕上，
> 在不同的操作系统中可以调用不同的绘制函数来绘制像素矩阵。系统需具有较好的扩展性以支持新的文件格式和操作系统。

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220504230832.png)

```java
/**
 * 像素矩阵类，这是一个辅助类。各种格式的图像文件最终都被转化为像素矩阵，不同的操作系统提供不同的方法显示像素矩阵
 *
 * @author 10545
 * @date 2022/5/4 23:06
 */
public class Matrix {
    //代码省略
}

/**
 * 抽象操作系统实现类，充当实现类接口
 *
 * @author 10545
 * @date 2022/5/4 23:11
 */
public interface ImageImp {
    //显示图像矩阵m
    public void doPaint(Matrix m);
}

/**
 * Windows操作系统实现类，充当具体实现类
 *
 * @author 10545
 * @date 2022/5/4 23:13
 */
public class WindowsImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用windows系统的绘制函数绘制像素矩阵
        System.out.print("在windows系统中显示图像:");
    }
}

/**
 * Linux操作系统实现类，充当具体实现类
 *
 * @author 10545
 * @date 2022/5/4 23:22
 */
public class LinuxImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用Linux系统的绘制函数绘制像素矩阵
        System.out.print("在Linux操作系统中显示图像:");
    }
}

/**
 * UNIX操作系统实现类，充当具体实现类
 *
 * @author 10545
 * @date 2022/5/4 23:26
 */
public class UnixImp implements ImageImp {
    @Override
    public void doPaint(Matrix m) {
        //调用UNIX系统的绘制函数绘制像素矩阵
        System.out.print("在UNIX系统中显示图像:");
    }
}

/**
 * 抽象图像类，充当抽象类
 *
 * @author 10545
 * @date 2022/5/4 23:33
 */
public abstract class Image {
    protected ImageImp imp;

    //注入实现类接口对象
    public void setImageImp(ImageImp imp) {
        this.imp = imp;
    }

    public abstract void parseFile(String fileName);
}

/**
 * JPG格式图像类，充当扩充抽象类
 *
 * @author 10545
 * @date 2022/5/4 23:50
 */
public class JPGImage extends Image {
    @Override
    public void parseFile(String fileName) {
        //模拟解析JPG文件并获得一个像素矩阵对象m
        Matrix m = new Matrix();
        imp.doPaint(m);
        System.out.println(fileName + ",格式为JPG");
    }
}

/**
 * PNG格式图像类，充当扩充抽象类
 *
 * @author 10545
 * @date 2022/5/7 21:20
 */
public class PNGImage extends Image {

    @Override
    public void parseFile(String fileName) {
        //模拟解析PNG文件并获得一个像素矩阵对象m
        Matrix m = new Matrix();
        imp.doPaint(m);
        System.out.println(fileName + ",格式为PNG");
    }
}

/**
 * BMP格式图像类，充当扩充抽象类
 *
 * @author 10545
 * @date 2022/5/7 21:25
 */
public class BMPImage extends Image {
    @Override
    public void parseFile(String fileName) {
        //模拟解析BMP文件并获得一个像素矩阵对象m
        Matrix m = new Matrix();
        imp.doPaint(m);
        System.out.println(fileName + ",格式为BMP");
    }
}

/**
 * GIF格式图像类，充当扩充抽象类
 *
 * @author 10545
 * @date 2022/5/7 21:32
 */
public class GIFImage extends Image {
    @Override
    public void parseFile(String fileName) {
        //模拟解析GIF文件并获得一个像素矩阵对象m
        Matrix m = new Matrix();
        imp.doPaint(m);
        System.out.println(fileName + ",格式为GIF");
    }
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<config>
    <!--RefinedAbstraction-->
    <className>cn.com.lgs.bridge_pattern.JPGImage</className>
    <!--ConcreteImplementor-->
    <className>cn.com.lgs.bridge_pattern.WindowsImp</className>
</config>
```

```java
/**
 * 测试类
 *
 * @author 10545
 * @date 2022/5/8 23:03
 */
public class Demo {
  public static void main(String[] args) {
    Image image;
    ImageImp imp;
    image = (Image) XMLUtil.getBean("_java/design_patterns/src/main/java/com/luguosong/_04_structural/_02_bridge_pattern/config.xml").get(0);
    imp = (ImageImp) XMLUtil.getBean("_java/design_patterns/src/main/java/com/luguosong/_04_structural/_02_bridge_pattern/config.xml").get(1);
    //依赖注入
    image.setImageImp(imp);

    image.parseFile("小龙女");
  }
}
```

运行结果：

```text
在windows系统中显示图像:小龙女,格式为JPG
```

## 模式拓展

### 桥接模式与适配器模式配合使用

桥接模式和适配器模式用于设计的不同阶段，桥接模式用于系统的初步设计，
对于存在两个独立变化维度的类可以将其分为抽象化和实现化两个角色，使它们可以分别进行变化；
而在初步设计完成之后，当发现系统与已有类无法协同工作时，可以采用适配器模式。
但有时候在设计初期也需要考虑适配器模式，特别是那些涉及到大量第三方应用接口的情况。

举例说明：在某系统的报表处理模块中，需要将 `报表显示`和 `数据采集`分开，系统可以有 `多种报表显示方式`也可以有 `多种数据采集方式`，
如可以从文本文件中读取数据，也可以从数据库中读取数据，还可以从Excel文件中获取数据。如果需要从Excel文件中获取数据，
则需要调用与Excel相关的API，而这个API是现有系统所不具备的，该API由厂商提供。使用适配器模式和桥接模式设计该模块。

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205091052857.png)

## 效果

- 优点

    - 使用 `对象间的关联关系`解耦了 `抽象`和 `实现`之间固有的绑定关系。使得抽象和实现可以沿着各自的维度来变化。
      所谓抽象和实现沿着各自维度的变化，也就是说抽象和实现不再在同一个继承层次结构中，而是 `子类化`它们，
      使它们各自都具有自己的子类，以便任何组合子类，从而获得多维度组合对象。
    - 在很多情况下，桥接模式可以取代多层继承方案，多层继承方案违背了 `单一职责原则`，复用性较差，且类的个数非常多，
      桥接模式是比多层继承方案更好的解决方法，它极大减少了子类的个数。
    - 桥接模式提高了系统的可扩展性，在两个变化维度中任意扩展一个维度，都不需要修改原有系统，符合 `开闭原则`。
- 缺点

    - 桥接模式的使用会增加系统的理解与设计难度，由于关联关系建立在抽象层，要求开发者一开始就针对抽象层进行设计与编程。
    - 桥接模式要求正确识别出系统中两个独立变化的维度，因此其使用范围具有一定的局限性，如何正确识别两个独立维度也需要一定的经验积累。

## 模式适用性

- 如果一个系统需要在抽象化和具体化之间增加更多的灵活性，避免在两个层次之间建立静态的继承关系，通过桥接模式可以使它们在抽象层建立一个关联关系。
- `抽象部分`和 `实现部分`可以以继承的方式独立扩展而互不影响，在程序运行时可以动态将一个抽象化子类的对象和一个实现化子类的对象进行组合，即系统需要对抽象化角色和实现化角色进行动态耦合。
- 一个类存在两个（或多个）独立变化的维度，且这两个（或多个）维度都需要独立进行扩展。
- 对于那些不希望使用继承或因为多层继承导致系统类的个数急剧增加的系统，桥接模式尤为适用。

# 组合模式（Composite）

## 模式分类

结构型模式

对象模式

## 模式概述

组合多个对象形成树形结构以表示具有 `部分—整体`关系的层次结构。
组合模式对 `单个对象`（即叶子对象）和 `组合对象`（即容器对象）的使用具有 `一致性`，
又可以称为 `部分—整体`（Part-Whole）模式，它是一种对象结构型模式。

## 模式结构与实现

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220509235030.png)

- `Component（抽象构件）`：它可以是 `接口`或 `抽象类`，为 `叶子构件`和 `容器构件`对象声明接口，
  在该角色中可以包含所有子类共有行为的声明和实现。在抽象构件中定义了 `访问及管理它的子构件的方法`，
  例如增加子构件、删除子构件、获取子构件等。
- `Leaf（叶子构件）`：它在组合模式结构中表示叶子节点对象。叶子节点没有子节点，它实现了在抽象构件中定义的行为。
  对于那些访问及管理子构件的方法，可以通过捕获异常等方式进行处理。
- `Composite（容器构件）`：它在组合模式结构中表示容器节点对象。容器节点包含子节点，其子节点可以是叶子节点，
  也可以是容器节点。它提供一个集合用于存储子节点，实现了在抽象构件中定义的行为，包括那些访问及管理子构件的方法，
  在其业务方法中可以递归调用其子节点的业务方法。

## 实例代码

> Sunny软件公司欲开发一个杀毒（AntiVirus）软件，该软件既可以对某个文件夹（Folder）杀毒，
> 也可以对某个指定的文件（File）进行杀毒。该杀毒软件还可以根据各类文件的特点，为不同类型的文件提供不同的杀毒方式，
> 例如图像文件（ImageFile）和文本文件（TextFile）的杀毒方式就有所差异。现需要提供该杀毒软件的整体框架设计方案。

### 初始设计

```java
//为了突出核心框架代码，我们对杀毒过程的实现进行了大量简化

import java.util.*;

//图像文件类
class ImageFile {
    private String name;

    public ImageFile(String name) {
        this.name = name;
    }

    public void killVirus() {
        //简化代码，模拟杀毒
        System.out.println("----对图像文件'" + name + "'进行杀毒");
    }
}

//文本文件类
class TextFile {
    private String name;

    public TextFile(String name) {
        this.name = name;
    }

    public void killVirus() {
        //简化代码，模拟杀毒
        System.out.println("----对文本文件'" + name + "'进行杀毒");
    }
}

//文件夹类
class Folder {
    private String name;
    //定义集合folderList，用于存储Folder类型的成员
    private ArrayList<Folder> folderList = new ArrayList<Folder>();
    //定义集合imageList，用于存储ImageFile类型的成员
    private ArrayList<ImageFile> imageList = new ArrayList<ImageFile>();
    //定义集合textList，用于存储TextFile类型的成员
    private ArrayList<TextFile> textList = new ArrayList<TextFile>();

    public Folder(String name) {
        this.name = name;
    }

    //增加新的Folder类型的成员
    public void addFolder(Folder f) {
        folderList.add(f);
    }

    //增加新的ImageFile类型的成员
    public void addImageFile(ImageFile image) {
        imageList.add(image);
    }

    //增加新的TextFile类型的成员
    public void addTextFile(TextFile text) {
        textList.add(text);
    }

    //需提供三个不同的方法removeFolder()、removeImageFile()和removeTextFile()来删除成员，代码省略

    //需提供三个不同的方法getChildFolder(int i)、getChildImageFile(int i)和getChildTextFile(int i)来获取成员，代码省略

    public void killVirus() {
        System.out.println("****对文件夹'" + name + "'进行杀毒");  //模拟杀毒

        //如果是Folder类型的成员，递归调用Folder的killVirus()方法
        for (Object obj : folderList) {
            ((Folder) obj).killVirus();
        }

        //如果是ImageFile类型的成员，调用ImageFile的killVirus()方法
        for (Object obj : imageList) {
            ((ImageFile) obj).killVirus();
        }

        //如果是TextFile类型的成员，调用TextFile的killVirus()方法
        for (Object obj : textList) {
            ((TextFile) obj).killVirus();
        }
    }
}
```

```java
//客户端测试代码进行测试
class Client {
    public static void main(String args[]) {
        Folder folder1, folder2, folder3;
        folder1 = new Folder("Sunny的资料");
        folder2 = new Folder("图像文件");
        folder3 = new Folder("文本文件");

        ImageFile image1, image2;
        image1 = new ImageFile("小龙女.jpg");
        image2 = new ImageFile("张无忌.gif");

        TextFile text1, text2;
        text1 = new TextFile("九阴真经.txt");
        text2 = new TextFile("葵花宝典.doc");

        folder2.addImageFile(image1);
        folder2.addImageFile(image2);
        folder3.addTextFile(text1);
        folder3.addTextFile(text2);
        folder1.addFolder(folder2);
        folder1.addFolder(folder3);

        folder1.killVirus();
    }
}
```

运行结果如下：

```text
****对文件夹'Sunny的资料'进行杀毒
****对文件夹'图像文件'进行杀毒
----对图像文件'小龙女.jpg'进行杀毒
----对图像文件'张无忌.gif'进行杀毒
****对文件夹'文本文件'进行杀毒
----对文本文件'九阴真经.txt'进行杀毒
----对文本文件'葵花宝典.doc'进行杀毒
```

### 存在的问题

- 文件夹类Folder的设计和实现都非常复杂，需要定义多个集合存储不同类型的成员，而且需要针对不同的成员提供增加、删除和获取等管理和访问成员的方法，存在大量的冗余代码，系统维护较为困难。
- 由于系统没有提供抽象层，客户端代码必须有区别地对待充当容器的文件夹Folder和充当叶子的ImageFile和TextFile，无法统一对它们进行处理。
- 系统的灵活性和可扩展性差，如果需要增加新的类型的叶子或容器都需要对原有代码进行修改。例如，如果需要在系统中增加一种新类型的视频文件VideoFile，则必须修改Folder类的源代码，否则无法在文件夹中添加视频文件。

### 使用组合模式实现

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205100943698.png)

```java
/**
 * 抽象文件类，充当抽象构件类
 *
 * @author luguosong
 * @date 2022/5/10 16:53
 */
public abstract class AbstractFile {
    public abstract void add(AbstractFile file);

    public abstract void remove(AbstractFile file);

    public abstract AbstractFile getChild(int i);

    public abstract void killVirus();
}

/**
 * 图像文件类，充当叶子构建类
 *
 * @author luguosong
 * @date 2022/5/10 16:59
 */
public class ImageFile extends AbstractFile {

    private String name;

    public ImageFile(String name) {
        this.name = name;
    }

    @Override
    public void add(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    @Override
    public void remove(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    @Override
    public AbstractFile getChild(int i) {
        System.out.println("对不起，不支持该方法！");
        return null;
    }

    @Override
    public void killVirus() {
        //模拟杀毒
        System.out.println("---对图像文件'" + name + "'进行杀毒");
    }
}

/**
 * 文本文件类，充当叶子构件类
 *
 * @author luguosong
 * @date 2022/5/10 17:02
 */
public class TextFile extends AbstractFile {

    private String name;

    public TextFile(String name) {
        this.name = name;
    }

    @Override
    public void add(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    @Override
    public void remove(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    @Override
    public AbstractFile getChild(int i) {
        System.out.println("对不起，不支持该方法！");
        return null;
    }

    @Override
    public void killVirus() {
        //模拟杀毒
        System.out.println("---对文本文件'" + name + "'进行杀毒");
    }
}

/**
 * 视频文件类，充当叶子构件类
 *
 * @author luguosong
 * @date 2022/5/10 17:23
 */
public class VideoFile extends AbstractFile {

    private String name;

    public VideoFile(String name) {
        this.name = name;
    }

    @Override
    public void add(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    @Override
    public void remove(AbstractFile file) {
        System.out.println("对不起，不支持该方法！");
    }

    @Override
    public AbstractFile getChild(int i) {
        System.out.println("对不起，不支持该方法！");
        return null;
    }

    @Override
    public void killVirus() {
        //模拟杀毒
        System.out.println("---对视频文件'" + name + "'进行杀毒");
    }
}

/**
 * 文件夹类，充当容器构件类
 *
 * @author luguosong
 * @date 2022/5/10 17:29
 */
public class Folder extends AbstractFile {

    //定于集合fileList,用于存储AbstractFile类型的成员
    private List<AbstractFile> fileList = new ArrayList<AbstractFile>();

    private String name;

    public Folder(String name) {
        this.name = name;
    }

    @Override
    public void add(AbstractFile file) {
        fileList.add(file);
    }

    @Override
    public void remove(AbstractFile file) {
        fileList.remove(file);
    }

    @Override
    public AbstractFile getChild(int i) {
        return fileList.get(i);
    }

    @Override
    public void killVirus() {
        //模拟杀毒
        System.out.println("***对文件夹'" + name + "'进行杀毒");

        //递归调用成员构件的killVirus()方法
        for (AbstractFile abstractFile : fileList) {
            abstractFile.killVirus();
        }
    }
}
```

```java
/**
 * 客户端测试类
 *
 * @author luguosong
 * @date 2022/5/10 17:42
 */
public class Demo {
    public static void main(String[] args) {
        //针对抽象构件编程
        AbstractFile file1, file2, file3, file4, file5, folder1, folder2, folder3, folder4;

        folder1 = new Folder("Sunny的资料");
        folder2 = new Folder("图像文件");
        folder3 = new Folder("文本文件");
        folder4 = new Folder("视频文件");

        file1 = new ImageFile("小龙女.jpg");
        file2 = new ImageFile("张无忌.gif");
        file3 = new TextFile("九阴真经.txt");
        file4 = new TextFile("葵花宝典.doc");
        file5 = new VideoFile("笑傲江湖.rmvb");

        folder2.add(file1);
        folder2.add(file2);
        folder3.add(file3);
        folder3.add(file4);
        folder4.add(file5);
        folder1.add(folder2);
        folder1.add(folder3);
        folder1.add(folder4);

        //从“Sunny的资料”节点开始进行杀毒操作
        folder1.killVirus();
    }
}
```

运行结果：

```text
***对文件夹'Sunny的资料'进行杀毒
***对文件夹'图像文件'进行杀毒
---对图像文件'小龙女.jpg'进行杀毒
---对图像文件'张无忌.gif'进行杀毒
***对文件夹'文本文件'进行杀毒
---对文本文件'九阴真经.txt'进行杀毒
---对文本文件'葵花宝典.doc'进行杀毒
***对文件夹'视频文件'进行杀毒
---对视频文件'笑傲江湖.rmvb'进行杀毒
```

## 模式拓展

### 透明组合模式

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220510211437.png)

`抽象构件Component`中声明了所有用于管理成员对象的方法，包括add（）、remove（）以及getChild（）等方法

透明组合模式也是组合模式的`标准形式`

- 优点
    - 确保所有的构件类都有相同的接口。在客户端看来，`叶子对象`与`容器对象`所提供的方法是`一致的`，客户端可以相同地对待所有的对象。
- 缺点
    - 对于`叶子对象`而言add（）、remove（）以及getChild（）等方法是没有意义的

### 安全组合模式

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220510211451.png)

`抽象构件Component`中没有声明任何用于管理成员对象的方法，而是在Composite类中声明并实现这些方法。

- 优点
    - 这种做法是安全的，因为根本不向叶子对象提供这些管理成员对象的方法，对于叶子对象，客户端不可能调用到这些方法，这就是解决方案2所采用的实现方式。
- 缺点
    - 且容器构件中那些用于管理成员对象的方法没有在抽象构件类中定义，因此客户端不能完全针对抽象编程。

## 效果

- 优点
    - 组合模式的关键是定义了一个 `抽象构件类`，它既可以代表叶子，又可以代表容器。客户端针对该抽象构件类进行编程，
      无须知道它到底表示的是叶子还是容器，可以对其进行统一处理。
    - `容器对象`与 `抽象构件类`之间还建立一个 `聚合关联关系`，在容器对象中既可以包含叶子，也可以包含容器，以此实现递归组合，形成一个树形结构。
    - 组合模式可以清楚地定义分层次的复杂对象，表示对象的全部或部分层次。它让客户端忽略了层次的差异，方便对整个层次结构进行控制。
    - 客户端可以一致地使用一个组合结构或其中单个对象，不必关心处理的是单个对象还是整个组合结构，`简化了客户端代码`。
    - 在组合模式中增加新的容器构件和叶子构件都很方便，无须对现有类库进行任何修改，符合开闭原则。
    - 组合模式为树形结构的面向对象实现提供了一种灵活的解决方案。通过叶子对象和容器对象的递归组合，可以形成复杂的树形结构，但对树形结构的控制却非常简单。
- 缺点
    - 在`增加新构件`时很难对容器中的构件类型`进行限制`。例如在某个文件夹中只能包含文本文件。使用组合模式时，不能依赖类型系统来施加这些约束，因为它们都来自相同的抽象层。
      在这种情况下，必须通过在运行时进行类型检查来实现，这个实现过程较为复杂。

## 模式适用性

1. 在具有整体和部分的层次结构中，希望通过一种方式忽略整体与部分的差异，客户端可以一致性地对待它们。
2. 在一个使用面向对象语言开发的系统中需要处理一个树形结构。
3. 在一个系统中能够分离出叶子对象和容器对象，而且它们的类型不固定，将来需要增加一些新的类型。

## 模式应用

- Java SE中的AWT
- Swing包的设计
- 在XML解析
- 组织结构树处理
- 文件系统设计

# 装饰模式（Decorator）

## 模式分类

对象型模式

结构型模式

## 模式概述

动态地给一个对象增加一些额外的职责，就增加对象功能来说，装饰模式比生成子类实现更为灵活。

## 模式结构与实现

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220512225601.png)

- `Component（抽象构件）`：它是`具体构件`和`抽象装饰类`的共同父类，声明了在`具体构件`中实现的业务方法。
  它的引入可以使客户端以一致的方式处理未被装饰的对象以及装饰之后的对象，实现客户端的透明操作。
- `ConcreteComponent（具体构件）`：它是抽象构件类的子类，用于定义`具体的构件对象`，实现了在`抽象构件`中声明的方法，
  装饰器可以给它增加额外的职责（方法）。
- `Decorator（抽象装饰类）`：它也是抽象构件类的子类，用于给具体构件`增加职责`，但是具体职责在其子类中实现。
  它维护一个指向抽象构件对象的引用，通过该引用可以`调用装饰之前构件对象的方法`，并通过其子类扩展该方法，以达到装饰的目的。
- `ConcreteDecorator（具体装饰类）`：它是抽象装饰类的子类，负责`向构件添加新的职责`。
  每一个具体装饰类都定义了一些新的行为，可以调用在抽象装饰类中定义的方法，
  并可以增加新的方法用以扩充对象的行为。


## 实例代码

> Sunny软件公司基于面向对象技术开发了一套图形界面构件库Visual Component，该构件库提供了大量基本构件，
> 如窗体、文本框、列表框等。由于在使用该构件库时，用户经常要求定制一些特殊的显示效果，例如带滚动条的窗体、
> 带黑色边框的文本框、既带滚动条又带黑色边框的列表框等，因此经常需要对该构件库进行扩展以增强其功能

### 初始设计

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/20220512220137.png)

### 存在的问题

- 系统扩展麻烦，在某些编程语言中无法实现。如果用户需要一个既带滚动条又带黑色边框的窗体，
  需要多重继承。但现在很多面向对象编程语言，如Java、C＃等都不支持多重类继承。
- 代码重复。不只是窗体需要设置滚动条，文本框、列表框等都需要设置滚动条，
  这些具体实现过程基本相同，代码重复，不利于对系统进行修改和维护。
- 系统庞大，类的数目非常多。

### 不合理原因

- 不应该使用继承进行复用，而是应该多用关联少用继承

### 使用装饰模式实现

```java
/**
 * 抽象界面构件类，充当抽象构件类
 *
 * @author luguosong
 * @date 2022/5/15 16:17
 */
public abstract class Component {
    public abstract void display();
}

/**
 * 窗体类，充当具体构件类
 *
 * @author luguosong
 * @date 2022/5/15 16:19
 */
public class Windows extends Component{
  @Override
  public void display() {
    System.out.println("显示窗体");
  }
}

/**
 * 文本框类，充当具体构件类
 *
 * @author luguosong
 * @date 2022/5/15 16:20
 */
public class TextBox extends Component{
  @Override
  public void display() {
    System.out.println("显示文本框");
  }
}

/**
 * 列表框类，充当具体构件类
 *
 * @author luguosong
 * @date 2022/5/15 16:22
 */
public class ListBox extends Component{
  @Override
  public void display() {
    System.out.println("显示列表框");
  }
}

/**
 * 构件装饰类，充当抽象装饰类
 *
 * @author luguosong
 * @date 2022/5/15 16:24
 */
public class ComponentDecorator extends Component {

  /**
   * 维持对抽象构件类型对象的引用
   */
  private Component component;

  //注入抽象构件类型的对象
  public ComponentDecorator(Component component) {
    this.component = component;
  }

  @Override
  public void display() {
    component.display();
  }
}

/**
 * 滚动条装饰类，充当具体装饰类
 *
 * @author luguosong
 * @date 2022/5/15 16:28
 */
public class ScrollBarDecorator extends ComponentDecorator{

  public ScrollBarDecorator(Component component) {
    super(component);
  }

  /**
   * 重写抽象装饰类方法
   */
  @Override
  public void display() {
    this.setScrollBar();
    super.display();
  }

  /**
   * 装饰方法
   */
  public void setScrollBar(){
    System.out.println("为构件增加滚动条");
  }
}

/**
 * 黑色边框装饰类，充当具体装饰类
 *
 * @author luguosong
 * @date 2022/5/15 16:38
 */
public class BlackBorderDecorator extends ComponentDecorator{
  public BlackBorderDecorator(Component component) {
    super(component);
  }

  /**
   * 重写抽象装饰类方法
   */
  @Override
  public void display() {
    setBlackBorder();
    super.display();
  }

  /**
   * 装饰方法
   */
  public void setBlackBorder(){
    System.out.println("为构件增加黑色边框");
  }
}
```

```java
/**
 * 测试
 * 
 * @author luguosong
 * @date 2022/5/15 16:41
 */
public class Demo {
    public static void main(String[] args) {
        Component component,componentSB;
        component=new Windows();
        componentSB=new ScrollBarDecorator(component);
        componentSB.display();
    }
}
```

```text
为构件增加滚动条
显示窗体
```

## 模式拓展

### 透明装饰模式

在透明装饰模式中，要求客户端完全针对抽象编程。
装饰模式的透明性要求客户端程序不应该将对象声明为具体构件类型或具体装饰类型，
而应该全部声明为抽象构件类型。对于客户端而言，具体构件对象和具体装饰对象没有任何区别。

### 半透明装饰模式

用具体装饰类型来定义装饰之后的对象，而具体构件类型还是可以使用抽象构件类型来定义，
这种装饰模式即为半透明装饰模式。

## 效果

- 优点
    - 对于扩展一个对象的功能，装饰模式比继承更加灵活性，不会导致类的个数急剧增加。
    - 可以通过一种动态的方式来扩展一个对象的功能。通过配置文件可以在运行时选择不同的具体装饰类，
      从而实现不同的行为。
    - 可以对一个对象进行多次装饰。通过使用不同的具体装饰类以及这些装饰类的排列组合，
      可以创造出很多不同行为的组合，得到功能更为强大的对象。
    - 具体构件类与具体装饰类可以独立变化，用户可以根据需要增加新的具体构件类和具体装饰类，
      原有类库代码无须改变，符合开闭原则。
- 缺点
    - 使用装饰模式进行系统设计时将产生很多小对象。这些对象的区别在于它们之间相互连接的方式有所不同，
      而不是它们的类或者属性值有所不同。大量小对象的产生势必会占用更多的系统资源，在一定程度上影响程序的性能。
    - 装饰模式提供了一种比继承更加灵活机动的解决方案，但同时也意味着比继承更加易于出错，排错也很困难。
      对于多次装饰的对象，调试时寻找错误可能需要逐级排查，较为烦琐。

## 模式适用性

- 在不影响其他对象的情况下，以动态、透明的方式给单个对象添加职责。
- 当不能采用继承的方式对系统进行扩展或者采用继承不利于系统扩展和维护时可以使用装饰模式。
  不能采用继承的情况主要有两类：第1类是系统中存在大量独立的扩展，
  为支持每一种扩展或者扩展之间的组合将产生大量的子类，使得子类数目呈爆炸性增长；
  第2类是因为类已定义为不能被继承（如Java语言中的final类）。

# 外观模式（Facade）

## 别名

门面模式

## 模式分类

结构模式

对象模式

## 模式概述

外部与一个子系统的通信通过一个统一的外观角色进行，
为子系统中的一组接口提供一个一致的入口。外观模式定义了一个高层接口，
这个接口使得子系统更加容易使用。

## 模式结构与实现

外观模式没有一个一般化的类图描述，通常使用示意图来表示外观模式

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205151814148.png)

- `Facade（外观角色）`：在客户端可以调用这个角色的方法，
  在外观角色中可以知道相关的（一个或者多个）子系统的功能和责任。在正常情况下，
  它将所有从客户端发来的请求委派到相应的子系统中去，传递给相应的子系统对象处理。
- `SubSystem（子系统角色）`：在软件系统中可以有一个或者多个子系统角色。
  每个子系统可以不是一个单独的类，而是一个类的集合，它实现子系统的功能。
  每个子系统都可以被客户端直接调用，或者被外观角色调用，
  它处理由外观类传过来的请求。子系统并不知道外观的存在，对于子系统而言，
  外观角色仅仅是另外一个客户端而已。


## 实例代码

> Sunny软件公司欲开发一个可应用于多个软件的文件加密模块，
> 该模块可以对文件中的数据进行加密并将加密之后的数据存储在一个新文件中。
> 具体的流程包括3个部分，分别是读取源文件、加密、保存加密之后的文件。其中，
> 读取文件和保存文件使用流来实现，加密操作通过求模运算实现。这3个操作相对独立，
> 为了实现代码的独立重用，让设计更符合单一职责原则，这3个操作的业务代码封装在3个不同的类中。

### 初始设计

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205151731356.png)

### 存在问题

- FileReader类、CipherMachine类和FileWriter类经常会作为一个整体同时出现，
  但是如果按照上述方案进行设计和实现，在每次使用这3个类时，
  客户端代码需要与它们逐个进行交互，导致客户端代码较为复杂，
  且在每次使用它们时很多代码都将重复出现。
- 如果需要更换一个加密类，例如将CipherMachine类改为NewCipherMachine类，
  则所有使用该文件加密模块的代码都需要进行修改，系统维护难度增大，
  灵活性和可扩展性较差。

### 使用外观模式实现

```java
/**
 * 文件读取类，充当子系统
 *
 * @author luguosong
 * @date 2022/5/15 19:32
 */
public class FileReader {
    public String read(String fileNameSrc){
        System.out.print("读取文件，获取明文：");
        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream inFs = new FileInputStream(fileNameSrc);
            int data;
            while((data=inFs.read())!=-1){
                sb=sb.append((char)data);
            }
            inFs.close();
            System.out.println(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        } catch (IOException e) {
            System.out.println("文件操作错误");
        }
        return sb.toString();
    }
}

/**
 * 数据加密类，充当子系统类
 *
 * @author 10545
 * @date 2022/5/16 21:33
 */
public class CipherMachine {
  public String encrypt(String plainText) {
    System.out.print("数据加密，将明文转换为密文：");
    String es = "";
    for (int i = 0; i < plainText.length(); i++) {
      String c = String.valueOf(plainText.charAt(i) % 7);
      es += c;
    }
    System.out.println(es);
    return es;
  }
}

/**
 * 文件保存类，充当子系统类
 *
 * @author 10545
 * @date 2022/5/16 22:11
 */
public class FileWriter {
  public void write(String encryptStr,String fileNameDes){
    System.out.print("保存密文，写入文件。");
    try {
      FileOutputStream outFs = new FileOutputStream(fileNameDes);
      outFs.write(encryptStr.getBytes());
      outFs.close();
    } catch (FileNotFoundException e) {
      System.out.println("文件不存在！");
    } catch (IOException e) {
      System.out.println("文件操作错误！");
    }
  }
}

/**
 * 加密外观类，充当外观类
 *
 * @author 10545
 * @date 2022/5/16 22:19
 */
public class EncryptFacade {
  //维持对子系统对象的引用
  private FileReader reader;
  private CipherMachine cipher;
  private FileWriter writer;

  public EncryptFacade() {
    reader = new FileReader();
    cipher = new CipherMachine();
    writer = new FileWriter();
  }

  //调用子系统对象的业务方法
  public void fileEncrypt(String fileNameSrc, String fileNameDes) {
    String plainStr = reader.read(fileNameSrc);
    String encryptStr = cipher.encrypt(plainStr);
    writer.write(encryptStr, fileNameDes);
  }
}
```

```text
hello world!
```

```java
/**
 * 客户端测试类
 *
 * @author 10545
 * @date 2022/5/16 22:34
 */
public class Demo {
    public static void main(String[] args) {
        EncryptFacade ef = new EncryptFacade();
        ef.fileEncrypt("_java/design-pattern/src/main/java/cn/com/lgs/facade_pattern/src.txt","_java/design-pattern/src/main/java/cn/com/lgs/facade_pattern/des.txt");
    }
}
```

```text
读取文件，获取明文：hello world!
数据加密，将明文转换为密文：63336406232563
保存密文，写入文件。
```

## 模式拓展

### 抽象外观类

在标准的外观模式结构图中，如果需要增加、删除或更换与外观类交互的子系统类，必须修改外观类或客户端的源代码，
这将违背开闭原则。

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205171111086.png)

### 外观角色设计补充说明

- 可以使用单例设计外观类，从而确保系统中只有唯一一个访问子系统的入口，并降低对系统资源的消耗
- 在一个系统中可以设计多个外观类，每个外观类都负责和一些特定的子系统交互，向客户端提供相应的业务功能。

## 效果

- 优点
    - 对客户端屏蔽了子系统组件，减少了客户端所需处理的对象数目并使得子系统使用起来更加容易。通过引入外观模式，
      客户端代码将变得很简单，与之关联的对象也很少。
    - 实现了子系统与客户端之间的松耦合关系，这使得子系统的变化不会影响到调用它的客户端，只需要调整外观类即可。
    - 一个子系统的修改对其他子系统没有任何影响，而且子系统内部变化也不会影响到外观对象。
    - 只是提供了一个访问子系统的统一入口，并不影响客户端直接使用子系统类。
- 缺点
    - 不能很好地限制客户端直接使用子系统类，如果对客户端访问子系统类做太多的限制则减少了可变性和灵活性。
    - 如果设计不当，增加新的子系统可能需要修改外观类的源代码，这违背了开闭原则。

## 模式适用性

- 当要为访问一系列复杂的子系统提供一个简单入口时可以使用外观模式。
- 客户端程序与多个子系统之间存在很大的依赖性。引入外观类可以将子系统与客户端解耦，从而提高子系统的独立性和可移植性。
- 在层次化结构中，可以使用外观模式定义系统中每一层的入口，层与层之间不直接产生联系，而通过外观类建立联系，降低层之间的耦合度。
- 试图通过外观类为子系统增加新行为的做法是错误的。外观模式的用意是为子系统提供一个集中化和简化的沟通渠道，
  而不是向子系统加入新行为。新行为的增加应该通过修改原有子系统类或增加新的子系统类来实现，不能通过外观类来实现。


# 享元模式（Flyweight）

## 别名

轻量级模式

## 模式分类

结构型模式

对象模式

## 模式概述

运用共享技术有效地支持大量细粒度对象的复用。系统只使用少量的对象，而这些对象都很相似，状态变化很小，
可以实现对象的多次复用。

## 模式结构与实现

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205171442597.png)

- `Flyweight（抽象享元类）`：通常是一个接口或抽象类，在抽象享元类中声明了具体享元类公共的方法，
  这些方法可以向外界提供享元对象的内部数据（内部状态），同时也可以通过这些方法来设置外部数据（外部状态）。
- `ConcreteFlyweight（具体享元类）`：它实现了抽象享元类，其实例称为享元对象。在具体享元类中为内部状态提供了存储空间。
  通常，可以结合单例模式来设计具体享元类，为每个具体享元类提供唯一的享元对象。
- `UnsharedConcreteFlyweight（非共享具体享元类）`：并不是所有的抽象享元类的子类都需要被共享，不能被共享的子类可设计为非共享具体享元类。
  当需要一个非共享具体享元类的对象时可以直接通过实例化创建。
- `FlyweightFactory（享元工厂类）`：享元工厂类用于创建并管理享元对象，它针对抽象享元类编程，将各种类型的具体享元对象存储在一个享元池中。
  享元池一般设计为一个存储“键值对”的集合（也可以是其他类型的集合），可以结合工厂模式进行设计。当用户请求一个具体享元对象时，
  享元工厂提供一个存储在享元池中已创建的实例，或者创建一个新的实例（如果不存在的话）并返回新创建的实例，同时将其存储在享元池中。

## 实例代码

> Sunny软件公司欲开发一个围棋软件。通过对围棋软件进行分析，发现在围棋棋盘中包含大量的黑子和白子，它们的形状、大小都一模一样，只是出现的位置不同而已。
> 如果将每个棋子都作为一个独立的对象存储在内存中，将导致该围棋软件在运行时所需内存空间较大。如何降低运行代价、提高系统性能是Sunny公司开发人员需要解决的一个问题。
> 为了解决这个问题，Sunny公司开发人员决定使用享元模式来设计该围棋软件的棋子对象。

```java
/**
 * 围棋棋子类，充当抽象享元类
 *
 * @author 10545
 * @date 2022/5/17 21:30
 */
public abstract class IgoChessman {
    public abstract String getColor();

    public void display(Coordinates coord){
        System.out.println("棋子的颜色："+this.getColor()+",棋子的位置："+coord.getX()+","+coord.getY());
    }
}

/**
 * 黑色棋子类，充当具体享元类
 *
 * @author 10545
 * @date 2022/5/17 21:43
 */
public class BlackIgoChessman extends IgoChessman{
  @Override
  public String getColor() {
    return "黑色";
  }
}

/**
 * 白色棋子类，充当具体享元类
 *
 * @author 10545
 * @date 2022/5/17 21:49
 */
public class WhiteIgoChessman extends IgoChessman{
  @Override
  public String getColor() {
    return "白色";
  }
}

/**
 * 围棋棋子工厂类，充当享元工厂类。使用单例模式对其进行设计
 *
 * @author 10545
 * @date 2022/5/17 21:58
 */
public class IgoChessmanFactory {
  private static IgoChessmanFactory instance = new IgoChessmanFactory();
  private static Hashtable ht; //使用Hashtable来存储享元对象，充当享元池

  private IgoChessmanFactory() {
    ht = new Hashtable();
    IgoChessman black, white;
    black = new BlackIgoChessman();
    ht.put("b", black);
    white = new WhiteIgoChessman();
    ht.put("w", white);
  }

  //返回享元工厂唯一实例
  public static IgoChessmanFactory getInstance() {
    return instance;
  }

  //通过key获取存放在Hashtable中的享元对象
  public static IgoChessman getIgoChessman(String color) {
    return (IgoChessman) ht.get(color);
  }
}

/**
 * 坐标类
 *
 * @author luguosong
 * @date 2022/5/27 11:11
 */
public class Coordinates {
  private int x;
  private int y;

  public Coordinates(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
```

```java
/**
 * 客户端测试类
 *
 * @author 10545
 * @date 2022/5/17 23:36
 */
public class Demo {
    public static void main(String[] args) {
        IgoChessman black1,black2,black3,white1,white2;
        IgoChessmanFactory factory;

        //获取享元工厂对象
        factory = IgoChessmanFactory.getInstance();

        //通过享元工厂获取三颗黑子
        black1 = factory.getIgoChessman("b");
        black2 = factory.getIgoChessman("b");
        black3 = factory.getIgoChessman("b");
        System.out.println("判断两颗黑子是否相同：" + (black1==black2));

        //通过享元工厂获取两颗白子
        white1 = factory.getIgoChessman("w");
        white2 = factory.getIgoChessman("w");
        System.out.println("判断两颗白子是否相同：" + (white1==white2));

        //显示棋子
        black1.display(new Coordinates(1,2));
        black2.display(new Coordinates(3,4));
        black3.display(new Coordinates(1,3));
        white1.display(new Coordinates(2,5));
        white2.display(new Coordinates(2,4));

    }
}
```

```text
判断两颗黑子是否相同：true
判断两颗白子是否相同：true
棋子的颜色：黑色,棋子的位置：1,2
棋子的颜色：黑色,棋子的位置：3,4
棋子的颜色：黑色,棋子的位置：1,3
棋子的颜色：白色,棋子的位置：2,5
棋子的颜色：白色,棋子的位置：2,4
```

## 模式拓展

### 单纯享元模式和复合享元模式

单纯享元模式中所有具体享元类都是可以共享的：

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205271416536.png)

将一些单纯享元对象用`组合模式`加以组合可以形成复合享元对象。复合享元对象本身不能共享，但可以分解成单纯享元对象，然后可以分享。复合享元类的作用是使得其中的单纯享元类都有相同的外部状态（比如上面棋子的位置都一致）：

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205271445794.png)

### 享元模式与String类

JDK类库中的String类使用了享元模式

```java
class Demo {
   public  static void main(String args[]) {
      String  str1 = "abcd";
      String  str2 = "abcd";
      String  str3 = "ab" + "cd";
      String  str4 = "ab";
      str4  += "cd";
      
      System.out.println(str1  == str2);
      System.out.println(str1  == str3);
      System.out.println(str1  == str4);
      str2  += "e";
      System.out.println(str1  == str2);
   }
}
```

```text
true
true
false
false
```

## 效果

- 优点
  - 减少内存中对象的数量，使得相同或相似对象在内存中只保存一份，从而可以节约系统资源，提高系统性能
  - 享元模式的外部状态相对独立，而不会影响其内部状态，从而使享元对象可以在不同的环境中被共享
- 缺点
  - 享元模式使得系统变得复杂，需要分离出内部状态和外部状态，使得程序的逻辑复杂化
  - 为了使对象可以共享，享元模式需要将享元对象的部分状态外部化，而读取外部状态将使得运行时间变长。

## 模式适用性

- 一个系统有大量相同或者相似的对象，造成内存的大量耗费。
- 对象的大部分状态都可以外部化，可以将这些外部状态传入对象中。
- 在使用享元模式时需要维护一个存储享元对象的享元池，而这需要耗费一定的系统资源。因此，在需要多次重复使用同一享元对象时才值得使用享元模式。

# 代理模式（Proxy）

## 模式分类

结构型

对象型

## 模式概述

给某一个对象提供一个代理，并由代理对象控制对原对象的引用。

## 模式结构与实现

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205271523938.png)

- `Subject（抽象主题角色）`：它声明了真实主题和代理主题的共同接口，使得在任何使用真实主题的地方都可以使用代理主题，客户端通常需要针对抽象主题角色进行编程。
- `Proxy（代理主题角色）`：代理主题角色内部包含了对真实主题的引用，从而可以在任何时候操作真实主题对象。在代理主题角色中提供一个与真实主题角色相同的接口，以便在任何时候都可以替代真实主题。代理主题角色还可以控制对真实主题的使用，负责在需要的时候创建和删除真实主题对象，并对真实主题对象的使用加以约束。通常，在代理主题角色中，客户端在调用所引用的真实主题操作之前或之后还需要执行其他操作，而不仅仅是单纯调用真实主题对象中的操作。
- `RealSubject（真实主题角色）`：它定义了代理角色所代表的真实对象，在真实主题角色中实现了真实的业务操作，客户端可以通过代理主题角色间接调用真实主题角色中定义的操作。

## 实例代码

> Sunny软件公司承接了某信息咨询公司的收费商务信息查询系统的开发任务，该系统的基本需求如下：
> 
>（1）在进行商务信息查询之前用户需要通过身份验证，只有合法用户才能够使用该查询系统。
> 
>（2）在进行商务信息查询时，系统需要记录查询日志，以便根据查询次数收取查询费用。
> 
>Sunny软件公司开发人员已完成了商务信息查询模块的开发任务，他们希望能够以一种松耦合的方式向原有系统增加身份验证和日志记录功能。客户端代码可以无区别地对待原始的商务信息查询模块和增加新功能之后的商务信息查询模块，而且可能在将来还要在该信息查询模块中增加一些新的功能。

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205271552435.png)

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205271553875.png)

```java
/**
 * 身份验证类
 * @author luguosong
 * @date 2022/5/27 15:57
 */
public class AccessValidator {
    public boolean validate(String userId){
        System.out.println("在数据库中验证用户'"+userId+"'是否为合法用户？");
        if (userId.equalsIgnoreCase("杨过")){
            System.out.println("登录成功");
            return true;
        }else {
            System.out.println("登录失败");
            return false;
        }
    }
}

/**
 * 日志记录类
 * @author luguosong
 * @date 2022/5/27 16:52
 */
public class Logger {
  public void log(String userId){
    System.out.println("更新数据库，用户'"+userId+"'查询次数加1！");
  }
}

/**
 * 抽象查询类，充当抽象主题角色
 * @author luguosong
 * @date 2022/5/27 17:10
 */
public interface Searcher {
  public String doSearch(String userId,String keyword);
}

/**
 * 具体查询类，充当真实主题角色
 * @author luguosong
 * @date 2022/5/27 17:12
 */
public class RealSearcher implements Searcher{
  @Override
  public String doSearch(String userId, String keyword) {
    System.out.println("用户'"+userId+"'使用关键词'"+keyword+"'查询商务信息！");
    return "返回具体内容";
  }
}

/**
 * 代理查询类，充当代理主题角色
 *
 * @author luguosong
 * @date 2022/5/27 17:16
 */
public class ProxySearcher implements Searcher {

  private RealSearcher searcher = new RealSearcher();
  private AccessValidator validator;
  private Logger logger;

  @Override
  public String doSearch(String userId, String keyword) {
    if (this.validate(userId)) {
      String result = searcher.doSearch(userId, keyword);
      this.log(userId);
      return result;
    } else {
      return null;
    }
  }

  public boolean validate(String userId) {
    validator = new AccessValidator();
    return validator.validate(userId);
  }

  public void log(String userId) {
    logger = new Logger();
    logger.log(userId);
  }
}
```

```xml
<?xml version="1.0" encoding="utf-8" ?>
<config>
  <className>com.luguosong._04_structural._07_proxy_pattern.static_proxy.ProxySearcher</className>
</config>
```

```java
/**
 * 测试类
 * @author luguosong
 * @date 2022/5/27 18:49
 */
public class Demo {
  public static void main(String[] args) {
    Searcher searcher;
    searcher=(Searcher) XMLUtil.getBean("_java/design_patterns/src/main/java/com/luguosong/_04_structural/_07_proxy_pattern/static_proxy/config.xml").get(0);
    String result = searcher.doSearch("杨过", "玉女心经");
  }
}
```

```text
在数据库中验证用户'杨过'是否为合法用户？
登录成功
用户'杨过'使用关键词'玉女心经'查询商务信息！
更新数据库，用户'杨过'查询次数加1！
```

## 模式拓展

### 代理和装饰的区别

代理模式和装饰模式在实现时有些类似，但是代理模式主要是给真实主题类`增加一些全新的职责`，例如权限控制、缓冲处理、智能引用、远程访问等，这些职责与原有职责`不属于同一个问题域`。而装饰模式是通过装饰类为具体构件类增加一些相关的职责，是对原有职责的扩展，这些职责`属于同一问题域`。代理模式和装饰模式的目的也不相同，前者是`控制对对象的访问`，而后者是为对象`动态地增加功能`。

### 远程代理

远程业务对象在本地主机中有一个代理对象，该代理对象负责对远程业务对象的访问和网络通信，它对于客户端对象而言是透明的。客户端无须关心实现具体业务的是谁，只需要按照服务接口所定义的方式直接与本地主机中的代理对象交互即可。

![](https://cdn.jsdelivr.net/gh/guosonglu/images@master/blog-img/202205311417379.png)

在Java语言中，可以通过RMI（Remote Method Invocation，远程方法调用）机制来实现远程代理，它能够实现一个Java虚拟机中的对象调用另一个Java虚拟机中对象的方法。在RMI中，客户端对象可以通过一个桩（Stub）对象与远程主机上的业务对象进行通信。由于桩对象和远程业务对象接口一致，因此对于客户端而言，操作远程对象和本地桩对象没有任何区别，桩对象就是远程业务对象在本地主机的代理对象。

在RMI实现过程中，远程主机端有一个Skeleton（骨架）对象来负责与Stub对象通信，RMI的基本实现步骤如下：

1. 客户端发起请求，将请求转交至RMI客户端的Stub类。
2. Stub类将请求的接口、方法、参数等信息进行序列化。
3. 将序列化后的流使用Socket传输至服务器端。
4. 服务器端接收到流后将其转发至相应的Skeleton类。
5. Skeleton类将请求信息反序列化后调用实际的业务处理类。
6. 业务处理类处理完毕后将结果返回给Skeleton类。
7. Skeleton类将结果序列化，再次通过Socket将流传送给客户端的Stub。
8. Stub在接收到流后进行反序列化，将反序列化后得到的Java Object对象返回给客户端调用者。

至此，一次完整的远程方法调用得以完成。

除了RMI之外，在Java语言中还可以通过很多其他方式来实现远程通信和远程方法调用，例如XML-RPC、Binary-RPC、JBoss-Remoting、Spring-Remoting、Hessian等。

### 虚拟代理

对于一些占用系统资源较多或者加载时间较长的对象，可以给这些对象提供一个虚拟代理。

在真实对象创建成功之前，虚拟代理扮演真实对象的替身。而当真实对象创建之后，虚拟代理将用户的请求转发给真实对象。

在以下两种情况下可以考虑使用虚拟代理：

- 由于对象本身的复杂性或者网络等原因导致一个对象需要较长的加载时间，此时可以用一个加载时间相对较短的代理对象来代表真实对象。通常在实现时可以结合多线程技术，一个线程用于显示代理对象，其他线程用于加载真实对象。这种虚拟代理模式可以应用在程序启动的时候，由于创建代理对象在时间和处理复杂度上要少于创建真实对象，因此，在程序启动时，可以用代理对象代替真实对象初始化，大大加速系统的启动时间。当需要使用真实对象时，再通过代理对象来引用，而此时真实对象可能已经成功加载完毕，可以缩短用户的等待时间。
- 当一个对象的加载十分耗费系统资源的时候，也非常适合使用虚拟代理。虚拟代理可以让那些占用大量内存或处理起来非常复杂的对象推迟到使用它们的时候才创建，而在此之前用一个相对来说占用资源较少的代理对象来代表真实对象，再通过代理对象来引用真实对象。为了节省内存，在第一次引用真实对象时再创建对象，并且该对象可被多次重用，在以后每次访问时需要检测所需对象是否已经被创建，因此在访问该对象时需要进行存在性检测，这需要消耗一定的系统时间，但是可以节省内存空间，这是一种用时间换取空间的做法。

### Java动态代理

让系统在运行时根据实际需要来动态创建代理类。

动态代理是一种较为高级的代理模式，它在事务管理、AOP（Aspect-Oriented Programming，面向方面编程）等领域都发挥了重要的作用。

Java语言实现动态代理需要用到`java.lang.reflect`包中的一些类。

- Proxy类

```java
public class Proxy implements java.io.Serializable {
  /**
   * @param loader 代理类的类加载器
   * @param interfaces 代理类的接口数组（与真实主题类的接口列表一致）
   * @return 返回一个Class类型的代理类
   * @throws IllegalArgumentException
   */
  public static Class<?> getProxyClass(ClassLoader loader,
                                       Class<?>... interfaces)
          throws IllegalArgumentException
  {
      //代码省略
  }

  /**
   * @param loader 代理类的类加载器
   * @param interfaces 代理类所实现的接口列表（与真实主题类的接口列表一致）
   * @param h 所指派的调用处理程序类
   * @return 返回一个动态创建的代理类的实例
   * @throws IllegalArgumentException
   */
  public static Object newProxyInstance(ClassLoader loader,
                                        Class<?>[] interfaces,
                                        InvocationHandler h)
          throws IllegalArgumentException
  {
      //代码省略
  }
}
```

- InvocationHandler接口

该接口作为代理实例的调用处理者的公共父类。每个代理类的实例都可以提供一个相关的具体调用处理者（InvocationHandler接口的子类）。

```java
public interface InvocationHandler {
  /**
   * 用于处理对代理类实例的方法调用并返回相应的结果
   * @param proxy 代理类的实例
   * @param method 表示需要代理的方法
   * @param args  代理方法的参数数组
   * @return
   * @throws Throwable
   */
  public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable;
}
```

对实例代码的静态代理进行动态代理改造：

```java
/**
 * @author luguosong
 * @date 2022/5/31 16:09
 */
public class SearchHandler implements InvocationHandler {

    private Searcher searcher;

    private AccessValidator accessValidator;

    private Logger logger;

    /**
     * 构造方法进行依赖注入
     *
     * @param searcher
     * @param accessValidator
     * @param logger
     */
    public SearchHandler(Searcher searcher, AccessValidator accessValidator, Logger logger) {
        this.searcher = searcher;
        this.accessValidator = accessValidator;
        this.logger = logger;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (accessValidator.validate((String) args[0])) {
            Object invoke = method.invoke(searcher, args);
            logger.log((String) args[0]);
            return invoke;
        } else {
            return null;
        }
    }
}
```

```java
/**
 * @author luguosong
 * @date 2022/5/31 16:34
 */
public class Demo {
    public static void main(String[] args) {
        RealSearcher realSearcher = new RealSearcher();
        AccessValidator accessValidator = new AccessValidator();
        Logger logger = new Logger();
        SearchHandler searchHandler = new SearchHandler(realSearcher, accessValidator, logger);

        Searcher searchProxy = (Searcher) Proxy.newProxyInstance(
                realSearcher.getClass().getClassLoader(),
                realSearcher.getClass().getInterfaces(),
                searchHandler);

        searchProxy.doSearch("杨过", "玉女心经");
    }
}
```

JDK中提供的动态代理只能代理一个或多个接口，如果需要动态代理具体类或抽象类，可以使用CGLib（Code Generation Library）等工具。CGLib是一个功能较为强大、性能和质量也较好的代码生成包，在许多AOP框架中得到了广泛应用。

## 效果

- 优点
  - 代理模式能够协调调用者和被调用者，在一定程度上降低了系统的耦合度，满足迪米特法则。
  - 客户端可以针对抽象主题角色进行编程，增加和更换代理类无须修改源代码，符合开闭原则，系统具有较好的灵活性和可扩展性。
  - 远程代理为位于两个不同地址空间对象的访问提供了一种实现机制，可以将一些消耗资源较多的对象和操作移至性能更好的计算机上，提高系统的整体运行效率。
  - 虚拟代理通过一个消耗资源较少的对象来代表一个消耗资源较多的对象，可以在一定程度上节省系统的运行开销。
  - 保护代理可以控制对一个对象的访问权限，为不同用户提供不同级别的使用权限。
- 缺点
  - 由于在客户端和真实主题之间增加了代理对象，因此有些类型的代理模式可能会造成请求的处理速度变慢，例如保护代理。
  - 实现代理模式需要额外的工作，有些代理模式的实现非常复杂，例如远程代理。

## 模式适用性

- 当客户端对象需要访问远程主机中的对象时，可以使用远程代理。
- 当需要用一个消耗资源较少的对象来代表一个消耗资源较多的对象，从而降低系统开销、缩短运行时间时，可以使用虚拟代理。例如一个对象需要很长时间才能完成加载时。
- 当需要控制对一个对象的访问，为不同用户提供不同级别的访问权限时，可以使用保护代理。
- 当需要为某一个被频繁访问的操作结果提供一个临时存储空间，以供多个客户端共享访问这些结果时，可以使用缓冲代理。通过缓冲代理，系统无须在客户端每次访问时都重新执行操作，只需直接从临时缓冲区获取操作结果即可。
- 当需要为一个对象的访问（引用）提供一些额外的操作时，可以使用智能引用代理。
