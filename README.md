# PBColorWheelView

A quick university work. Create a wheel with different sections and different colors.

![demo](Screenshots/pbcolorwheelview.png) 

## Version
1.0.0  
## Installation

With Gradle simply add to your build.gradle
```sh
compile 'com.thefrenchtouch.lib:pbcolorwheelview:1.0.0'
```
or  Copy paste the pbcolorwheelview folder on your project
## How to use
 
```xml
<com.thefrenchtouch.pbcolorwheelview.widget.ColorWheelView
        android:layout_width="300dp"
        android:layout_height="300dp"

        app:stroke="25dp"
        app:colors="@array/array_colors"
        app:texts="@array/array_texts"
        app:textSize="15sp"
        app:textsColor="@array/array_texts_colors"
        app:src="@mipmap/ic_launcher"/>
``` 

## License

MIT License

Copyright (c) [2016] [Paul Bancarel]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
