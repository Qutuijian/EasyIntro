# EasyIntro [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-EasyIntro-green.svg?style=true)](https://android-arsenal.com/details/1/3448) [![Codacy Badge](https://api.codacy.com/project/badge/grade/e93c6273804a4dbc91a298a66fed99be)](https://www.codacy.com/app/aesshoferi/EasyIntro) [![Gitter](https://badges.gitter.im/meNESS/EasyIntro.svg)](https://gitter.im/meNESS/EasyIntro?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
![Image](https://raw.githubusercontent.com/meNESS/EasyIntro/master/banner.png)
> Does your application **contain an Intro**? Do you want to have it **up and running in less than 5 minutes**? Do you want your intro to follow the **Android Design Guidelines**? Do you need **flexibility**? Do you believe Google is wrong with not providing **native intro**? Do you want a **simple and easy** to understand API?

If any (or all) of these questions seem familiar, the **EasyIntro** is the perfect library for you.

**Never** waste your time again. It provides you with the easiest possible implementation of an intro for your application. There is an Activity and Fragment with lots of methods, provide custom page indicator, custom toggle indicators, custom colors, custom themes, custom slide transformers … No limits for customizations.

## A Quick Overview What's In
* The easiest possible integration
* Integrate in less than 5 minutes
* Compatible down to API level 13
* Quick and simple API
* Uses the AppCompat support library
* Follows the Google Material Design Guidelines
* Uses vector (.svg) icons
* **Overlay slides**
* **Back stack fully supported for overlay slides**
* Modify intro settings on the go
* Comes with multiple default indicators
* Limit your swipe direction to left and/or right
* Define which toggle indicators to be shown
* You may have all listeners you need
* Abstract toggle indicator
* Fragment to have easy access to your intro methods
* Comes with multiple default slide transformers
* Based on a ViewPager
* **RTL support**
* **Lock on specific slides**
* Customizable
* Tested and stable
* Absolutely no limits

## Screenshots
![Screenshots](https://raw.githubusercontent.com/meNESS/EasyIntro/master/screenshots/1.jpg)

## EasyIntro in Action
[![EasyIntro in Action](http://img.youtube.com/vi/JhRS1w-sTOM/0.jpg)](http://www.youtube.com/watch?v=JhRS1w-sTOM)

## Wiki
You surely can find more details on [the wiki page](https://github.com/meNESS/EasyIntro/wiki).

## Include to Project
### Provide the Gradle Dependency
Add the dependency:
```gradle
dependencies {
	compile 'io.github.meness:easyintro:x.y.z'
}
```
**Note:** Replace `x.y.z` with the latest version which can be found at [releases page](https://github.com/meNESS/EasyIntro/releases).
### Clone or Download `.zip` file
Clone this repository or download the compressed file, then extract to your computer. Simply import the `library` module to your project.

## Usage
Create your Fragments which extend `EasyIntroFragment` and provide your desired layout resource as you always do:
```java
public class IntroOneFragment extends EasyIntroFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.intro_one, container, false);
    }
}
```
**Note:** By extending `EasyIntroFragment` you have easier access to the intro methods and the layout bottom padding will be modified automatically.

Create an activity which extends `EasyIntro` and override the necessary `initIntro()` method:
```java
public class MyEasyIntro extends EasyIntro {
    
    // override callbacks you need
    
    @Override
    protected void initIntro() {
        // use withSlide() to add slides and
        // configure your app intro
    }
}
```
**Note:** For more details about listeners, intro settings, customizing colors, and all other things [visit wiki](https://github.com/meNESS/EasyIntro/wiki).

Define your activity in your `Manifest.xml`:
```xml
<activity
	android:name=".MyEasyIntro"
	android:theme="@style/EasyIntro"/>
```
**Note:** This is good to give your intro activity a theme which has `EasyIntro` as a parent.

## Libraries Used
- [Materialize](https://github.com/mikepenz/Materialize)
- [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)
- [SpringIndicator](https://github.com/chenupt/SpringIndicator)
- [CircleIndicator](https://github.com/ongakuer/CircleIndicator)

## Credits
- Mike Penz - [Github](https://github.com/mikepenz)
    - For the good `.README` appearance.

## Developed By
- Alireza Eskandarpour Shoferi
    - [Twitter](https://twitter.com/enormoustheory) - [aesshoferi@gmail.com](mailto:aesshoferi@gmail.com)

## License
    Copyright 2016 Alireza Eskandarpour Shoferi
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
		http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
