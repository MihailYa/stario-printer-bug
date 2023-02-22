# stario-printer-bug
Minimal code for reproducing ConcurrentModificationException bug in StarIO10 library for printers

Related issue: https://github.com/star-micronics/StarXpand-SDK-Android/issues/3

### How to reproduce
1. Run the application on any device.
2. Wait for the crash.
3. It could take from 10 seconds to 5 minutes until the crash happens

No additional requirements for the environment (as far as I know)

### What the code is doing
The code starts and stops printers search in a loop.

### The device that I'm using to reproduce the bug
Android 10  
Samsung SM-G960U1

### The exception that causes a crash

```
E/AndroidRuntime: FATAL EXCEPTION: DefaultDispatcher-worker-6
    Process: com.mikleya.starioprinterbug, PID: 32008
    java.util.ConcurrentModificationException
        at java.util.ArrayList$Itr.next(ArrayList.java:860)
        at com.starmicronics.stario10.stardevicediscoverymanager.c.a(Unknown Source:203)
        at com.starmicronics.stario10.stardevicediscoverymanager.c$a.invokeSuspend(Unknown Source:12)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
        at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
        at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:570)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:677)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:664)
    	Suppressed: kotlinx.coroutines.DiagnosticCoroutineContextException: [StandaloneCoroutine{Cancelling}@8020ebc, Dispatchers.Default]
```