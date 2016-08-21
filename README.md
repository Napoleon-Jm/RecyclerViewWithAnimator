# RecyclerViewWithAnimator
Implements a recyclerView with a bounce animator.

模仿IOS ListView 边界bounce效果

##使用方法：
将自定义的BounceHorizontalView包裹在RecyclerView里面，
理论上也支持其他view的bounce的效果，对原代码没有侵入。

##Bug:
解决23.03版本以下的RecyclerView的wrap_content属性
measure不准的bug。<如果版本低于23.03请使用HorizontalLayoutManager>
