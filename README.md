#### KScoreBar

this view is use to the match score show 
also can show with anim

##### screenshot


##### custom attributes

```

<attr name="leftColor" format="color"/>
<attr name="rightColor" format="color"/>
<attr name="leftText" format="string"/>
<attr name="rightText" format="string"/>
<attr name="textColor" format="color"/>
<attr name="leftProgress" format="integer"/>
<attr name="rightProgress" format="integer"/>

```


#### show with anim e.g.

```

mScoreBar.setScoreAnimatorListener(this);
mScoreBar.addLeftProgressWithAnim(true, 10,20,25,30);
mScoreBar.addRightProgressWitAnim(true,0,8,13,45);
mScoreBar.showWithAnim(10000);
```