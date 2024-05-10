#-keep,allowobfuscation @interface korlibs.io.annotations.Keep
-keep @korlibs.annotations.Keep public class *
-keepclassmembers class * {
    @korlibs.annotations.Keep *;
}