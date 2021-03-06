# JDrawFunc
一个尝试使用后缀表达式绘制任意函数图像的小玩意（用于应付课设），使用IDEA开发

此项目的详细介绍：[使用后缀表达式绘制一元函数图像](http://melonl.net/2020/08/15/rpn/)

实现的功能：
* 根据用户输入的函数表达式绘制函数图像（支持 + - * / log ln ^ sqrt sin cos 运算符，支持小数、负号。表达式注意添加括号，否则解析出错）
* 可在画板中使用鼠标拖动视角
* 大概没什么问题的坐标轴绘制
* 支持调整坐标轴的单位长度（1-100的整数）

Bugs/缺陷：
* 某些函数的图像的线条粗细不一致（~~用点描线的缺点~~可能要使用更好的采样方式解决）
* 大部分定义域不是整个R的图像显示不正确（代码实现时候没想出合适的解决方案，暂时写死了）
* 不支持鼠标滚轮缩放图像比例尺（有点难，没实现）

可能更好的实现思路:
* 或许可以在原方法上改善绘制点的x坐标采样的方式来实现更加平滑的绘制
* 使用C++提高性能
* 使用精度更高的数据类型来绘制点

另：代码实现得极其繁琐，基本上除了实现思路之外没有任何参考价值

预览图:
![image](https://s1.ax1x.com/2020/06/17/NEV7rQ.jpg)

