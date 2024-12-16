package com.ngplus.sideeffect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.ngplus.sideeffect.ui.theme.SideEffectTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SideEffectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainDisposableEffectComposableRecompositionWorkInCoroutine(name = "Android")
                }
            }
        }
    }
}

/**************LaunchedEffect*******************/
@Composable
fun OrderLaunchedEffect(name: String) {
    Log.i("Side_effect_Tuto", "before compose")
    Text(text = "Hello $name!")
    LaunchedEffect(key1 = true) {
        Log.i("Side_effect_Tuto", "LaunchedEffect")
    }
    Log.i("Side_effect_Tuto", "after compose")
}
// display
/*
    before compose
    after compose
    LaunchedEffect
    explanation : LaunchedEffect is launched only if the composition is successful
*/

/**************LaunchedEffect*******************/
@Composable
fun LaunchedEffectWhenStateChanged(name: String) {
    val changeableState = remember {
        mutableStateOf(0)
    }
    Log.i("Side_effect_Tuto", "before compose ")
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
        onClick = {
            changeableState.value += 1
            Log.i("Side_effect_Tuto", "Clicked")
        }
    ) {
        Text(text = "Increase ${changeableState.value}")
    }
    LaunchedEffect(key1 = true) {
        Log.i("Side_effect_Tuto", "LaunchedEffect")
    }
    Log.i("Side_effect_Tuto", "after compose ")
}
// display
/*
    before compose
    after compose
    LaunchedEffect
when clicked
    none
-------------
explanation :
LaunchedEffect launched once because of key is the same key
 */
/**************LaunchedEffect*******************/
@Composable
fun OrderLaunchedEffectWhenNothingChanged(name: String) {
    Log.i("Side_effect_Tuto", "before compose")
    val notChanged = remember { mutableStateOf(0) }
    Button(
        onClick = {
            notChanged.value = 0
            Log.i("Side_effect_Tuto", "Clicked")
        }
    ) {
        Text(text = "Increase ${notChanged.value}")

    }
    LaunchedEffect(key1 = notChanged) {
        Log.i("Side_effect_Tuto", "LaunchedEffect")
    }
    Log.i("Side_effect_Tuto", "after compose")
}
/*
display
before compose
after compose
LaunchedEffect
explanation
------
nothing is fired, because value is not changed
 */
/**************LaunchedEffect*******************/
@Composable
fun LaunchCoroutineLaunchedEffect(name: String) {
    val changeableState = remember {
        mutableStateOf(0)
    }
    Log.i("Side_effect_Tuto", "before compose ")
    Button(
        onClick = {
            changeableState.value += 1
            Log.i("Side_effect_Tuto", "Clicked")
        }
    ) {
        Text(text = "Increase ${changeableState.value}")
    }
    LaunchedEffect(key1 = true) {
        var i = 20
        while (i > 0) {
            delay(1000)
            i--
            Log.i("Side_effect_Tuto", "i still counting $i")
        }
        Log.i("Side_effect_Tuto", "LaunchedEffect")
    }
    Log.i("Side_effect_Tuto", "after compose ")
}
// display
/*
    before compose
    after compose
    i still counting 19
when clicked
    i still counting 18
    i still counting 17
when clicked
    i still counting 16
    i still counting 15
    i still counting 14
when clicked
    i still counting 13
    i still counting 12
    i still counting 11
..
..
    LaunchedEffect
-------------
Explanation : the code inside LaunchedEffect continue to operate even if a recomposition take place
 */
/**************LaunchedEffect*******************/
@Composable
fun LaunchedEffectWhenKeyLaunchedEffectChanged(name: String) {
    val changeableState = remember {
        mutableStateOf(0)
    }
    Log.i("Side_effect_Tuto", "before compose ")
    Button(
        onClick = { changeableState.value += 1 }
    ) {
        Text(text = "Increase ${changeableState.value}")
    }
    LaunchedEffect(key1 = changeableState.value) {
        Log.i("Side_effect_Tuto", "LaunchedEffect")
    }
    Log.i("Side_effect_Tuto", "after compose ")
}
// display
/*
    before compose
    after compose
    LaunchedEffect
when clicked
    before compose
    after compose
    LaunchedEffect
-----------
Explanation : given that the key change with each composition, LaunchedEffect is launched
 */
/**************LaunchedEffect*******************/
@Composable
fun LaunchCoroutineLaunchedEffectWhenKeyLaunchedEffectChanged(name: String) {
    val changeableState = remember {
        mutableStateOf(0)
    }
    Log.i("Side_effect_Tuto", "before compose ")
    Button(
        onClick = {
            changeableState.value += 1
            Log.i("Side_effect_Tuto", "Clicked")
        }
    ) {
        Text(text = "Increase ${changeableState.value}")
    }
    LaunchedEffect(key1 = changeableState.value) {
        var i = 20
        while (i > 0) {
            delay(1000)
            i--
            Log.i("Side_effect_Tuto", "i still counting $i")
        }
        Log.i("Side_effect_Tuto", "LaunchedEffect")
    }
    Log.i("Side_effect_Tuto", "after compose ")
}
// display
/*
    before compose
    after compose
    LaunchedEffect
    i still counting 19
    i still counting 18
    i still counting 17
when clicked
    before compose
    after compose
    LaunchedEffect
    i still counting 19
    i still counting 18
    i still counting 17
-----------
Explanation
In the first composition, we enter to LaunchedEffect as normal
since the key changes with each composition, when clicked, we authorize executing side Effect
 */

/***************LaunchedEffect******************/
@Composable
fun LaunchedEffectWhenLeaveTheRecomposition(name: String) {
    val changeableState = remember {
        mutableStateOf(true)
    }
    Log.i("Side_effect_Tuto", "before compose ")
    Button(
        onClick = {
            changeableState.value = !changeableState.value
            Log.i("Side_effect_Tuto", "clicked")
        }
    ) {
        Text(text = "Increase ${changeableState.value}")
    }
    if (changeableState.value) {
        LaunchedEffect(key1 = changeableState.value) {
            Log.i("Side_effect_Tuto", "LaunchedEffect")
        }
    }
    Log.i("Side_effect_Tuto", "after compose")
}
// display
/*
    before compose
    after compose
    LaunchedEffect
when clicked
    clicked
    before compose
    after compose
when clicked
    before compose
    after compose
    LaunchedEffect
//Explanation
    we know that LaunchedEffect is present when changeableState is true, and the key change,
    once the LaunchEffect is present in composition, what is inside is executed
 */
/**************LaunchedEffect*******************/
@Composable
fun LaunchSnackBarWhenStateChanged(name: String) {
    Log.i("Side_effect_Tuto", "after compose ")
    val changeableState = remember {
        mutableStateOf(false)
    }
    val scaffoldState = rememberScaffoldState()
    // when launched, SnackBar is not shown
    if (changeableState.value) {
        LaunchedEffect(key1 = scaffoldState.snackbarHostState) {
            scaffoldState.snackbarHostState.showSnackbar("ici text snackBar")
        }
    }
    Scaffold(scaffoldState = scaffoldState) {
        Button(onClick = { changeableState.value = !changeableState.value }) {
            Text("Toggle")
        }
    }
}
/*
Display

Explanation

 */
/*
The coroutine will be cancelled if LaunchedEffect leaves the composition
 src: https://developer.android.com/jetpack/compose/side-effects
If "LaunchedEffect" is recomposed with different keys (see the Restarting Effects section below),
the existing coroutine will be cancelled and the new suspend function will be launched in a new coroutine.
----------
In the code above, a coroutine is triggered if the state is true and it'll
be cancelled when it doesn't. As the LaunchedEffect call site is inside an
if statement, when the statement is false, if LaunchedEffect was in the Composition,
it'll be removed, and therefore, the coroutine will be cancelled.
 */
/**********SideEffect**********/
@Composable
fun MainSideEffectComposableRecomposition(name: String) {
    val stateChangeable = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = { stateChangeable.value = !stateChangeable.value }
    ) {
        Text("Changeable ! ${stateChangeable.value}")
    }
    SideEffect {
        Log.i("Side_effect_Tuto", "inside SideEffect")
    }
}
/*
display
    Side_effect_Tuto
Explanation
    triggers when recomposition is completed successfully
*/

/**********DisposableEffect**********/
/*
Disposable recomposition
 */
@Composable
fun MainDisposableEffectComposableRecomposition(name: String) {
    val stateChangeable = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = { stateChangeable.value = !stateChangeable.value }
    ) {
        Text("Changeable ! ${stateChangeable.value}")
    }
    if(stateChangeable.value){
        DisposableEffectComposableRecomposition(name = name)
    }
}

@Composable
fun DisposableEffectComposableRecomposition(name: String) {
    Log.i("Side_effect_Tuto", "before DisposableEffect")
    DisposableEffect(key1 = true) {
        Log.i("Side_effect_Tuto", "inside DisposableEffect")
        onDispose {
            Log.i("Side_effect_Tuto", "inside Dispose DisposableEffect")
        }
    }
    Text(name, fontSize = 23.sp)
    Log.i("Side_effect_Tuto", "after DisposableEffect")
}

/*
when launched
    none
when clicked
    before DisposableEffect
    after DisposableEffect
    inside DisposableEffect
when clicked
    inside Dispose DisposableEffect

Explanation
    When leave the composition, onDispose is triggered
*/
/*
Disposable recomposition
 */
@Composable
fun MainDisposableEffectComposableRecompositionWorkInCoroutine(name: String) {
    val stateChangeable = remember {
        mutableStateOf(true)
    }
    Button(
        onClick = { stateChangeable.value = !stateChangeable.value }
    ) {
        Text("Changeable ! ${stateChangeable.value}")
    }
    if(stateChangeable.value){
        DisposableEffectComposableRecompositionWorkInCoroutine(name = name)
    }
}

@Composable
fun DisposableEffectComposableRecompositionWorkInCoroutine(name: String) {
    Log.i("Side_effect_Tuto", "before DisposableEffect")
    val scope = rememberCoroutineScope()
    DisposableEffect(key1 = true) {
        Log.i("Side_effect_Tuto", "inside DisposableEffect")
        scope.launch {
            var i = 20
            while (i > 0) {
                delay(1000)
                Log.i("Side_effect_Tuto", "inside DisposableEffect some work $i")
                i--
            }
        }
        onDispose {
            scope.cancel()
            Log.i("Side_effect_Tuto", "i'am leaving the composition")
        }
    }
    Text(name, fontSize = 23.sp)
    Log.i("Side_effect_Tuto", "after DisposableEffect")
}

/*
when launched
    before DisposableEffect
    after DisposableEffect
    inside DisposableEffect
    inside DisposableEffect some work 20
    inside DisposableEffect some work 19
    inside DisposableEffect some work 18
    inside DisposableEffect some work 17
when clicked
    i'am leaving the composition
when clicked
    ...
*/
/*
Disposable leave the recomposition
 */
@Composable
fun MainDisposableEffectComposableWorkInCoroutineAndRecomposition(name: String) {
    val stateChangeable = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = { stateChangeable.value = !stateChangeable.value }
    ) {
        Text("Changeable ! ${stateChangeable.value}")
    }
    if (stateChangeable.value) {
        DisposableEffectComposableRecompositionWorkInCoroutine(name = name)
    }
}

/*
when launched
    none
when clicked
    before DisposableEffect
    after DisposableEffect
    inside DisposableEffect
    inside DisposableEffect some work 20
    inside DisposableEffect some work 19
    inside DisposableEffect some work 18
    inside DisposableEffect some work 17
when clicked
    inside Dispose DisposableEffect
when clicked
    before DisposableEffect
    after DisposableEffect
    inside DisposableEffect
    inside DisposableEffect some work 20
    inside DisposableEffect some work 19
    inside DisposableEffect some work 18
when clicked
    inside Dispose DisposableEffect
*/
/*
Disposable leave the recomposition
 */
@Composable
fun MainDisposableEffectComposable(name: String) {
    val stateChangeable = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = { stateChangeable.value = !stateChangeable.value }
    ) {
        Text("Changeable ! ${stateChangeable.value}")
    }
    if (stateChangeable.value) {
        DisposableEffectComposable(name = name)
    }
}

@Composable
fun DisposableEffectComposable(name: String) {
    Log.i("Side_effect_Tuto", "before DisposableEffect")
    DisposableEffect(key1 = true) {
        Log.i("Side_effect_Tuto", "inside DisposableEffect")
        onDispose {
            Log.i("Side_effect_Tuto", "inside Dispose DisposableEffect")
        }
    }
    Text(name, fontSize = 23.sp)
    Log.i("Side_effect_Tuto", "after DisposableEffect")
}
/*
when launched
    none
when clicked
    before DisposableEffect
    after DisposableEffect
    inside DisposableEffect
when clicked
    inside Dispose DisposableEffect
*/
/********************/
/*
Disposable state changed
 */
@Composable
fun MainDisposableEffectComposableWithState(name: String) {
    val stateChangeable = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = { stateChangeable.value = !stateChangeable.value }
    ) {
        Text("Changeable !")
    }
    DisposableEffectComposableWithState(name = name, state = stateChangeable.value)
}

@Composable
fun DisposableEffectComposableWithState(name: String, state: Boolean) {
    Log.i("Side_effect_Tuto", "before DisposableEffect")
    DisposableEffect(key1 = state) {
        val count = 1
        Log.i("Side_effect_Tuto", "inside DisposableEffect/$count")
        onDispose {
            Log.i("Side_effect_Tuto", "inside Dispose DisposableEffect/$count")
        }
    }
    Text(name, fontSize = 23.sp)
    Log.i("Side_effect_Tuto", "after DisposableEffect")
}

@Composable
fun MainLifecycleEventDisposableEffectComposableWithState(name: String) {
    val state = remember {
        mutableStateOf(false)
    }
    Button(onClick = { state.value = !state.value }) {
        Text("State")
    }
    LifecycleEventDisposableEffectComposableWithState(name, state.value)
}

@Composable
fun LifecycleEventDisposableEffectComposableWithState(name: String, state: Boolean) {
    Log.i("Side_effect_Tuto", "before DisposableEffect")
    val lifecycle = LocalLifecycleOwner.current
    DisposableEffect(key1 = state) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                Log.i("Side_effect_Tuto", "ON_START")
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        Log.i("Side_effect_Tuto", "inside DisposableEffect")
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
            Log.i("Side_effect_Tuto", "inside Dispose DisposableEffect/")
        }
    }
    Log.i("Side_effect_Tuto", "after DisposableEffect")
}

/**********rememberCoroutineScope***********/

@Composable
fun MainRememberCoroutineScope(name: String) {
    LaunchedEffect(key1 = false) {
    }
    val text = remember {
        mutableStateOf("text \n")
    }
    val scope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                var i = 1
                while (i < 10) {
                    delay(1_000)
                    Log.i("Side_effect_Tuto", "${i++}")
                    text.value = "$i  \n ${text.value}"
                }
            }
        }) {
            Text("launch count")
        }

        Button(onClick = {
            scope.cancel()
            Log.i("Side_effect_Tuto", "count is cancelled")
            text.value = "canceled ${text.value}"
        }) {
            Text("Cancel count")
        }
        Text(text.value)
    }
}
/***************rememberCoroutineScope******************/


/***************rememberUpdatedState******************/
@Composable
fun MainComposableWithoutRememberUpdateState(name: String) {

    var changeableIndex by remember {
        mutableStateOf("")
    }
    Log.i("Side_effect_Tuto", "outside 0")
    Button(
        onClick = { changeableIndex += "\nclicked" }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Text("Get $changeableIndex")
        }
    }
    ComposableWithoutRememberUpdateState(changeableIndex)
}

@Composable
fun ComposableWithoutRememberUpdateState(stateful: String) {
    Log.i("Side_effect_Tuto", "recall ComposableRememberUpdateState")
    LaunchedEffect(key1 = Unit) {
        Log.i("Side_effect_Tuto", "before delay")
        delay(2000)
        Log.i("Side_effect_Tuto", "after function $stateful")
    }
}
/*
launched
    outside 0
    recall ComposableRememberUpdateState
    before delay
    after function
clicked
    outside 0
    recall ComposableRememberUpdateState
clicked
    outside 0
    recall ComposableRememberUpdateState
 */
/***********************RememberUpdateState*************************/
@Composable
fun MainComposableRememberUpdateState(name: String) {
    var changeableIndex by remember {
        mutableStateOf("")
    }
    Log.i("Side_effect_Tuto", "outside 0")
    Button(
        onClick = { changeableIndex += "\nclicked" }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            Text("Get $changeableIndex")
        }
    }
    ComposableRememberUpdateState(changeableIndex)
}

@Composable
fun ComposableRememberUpdateState(stateful: String) {
    val changeableState by rememberUpdatedState(newValue = stateful)
    Log.i("Side_effect_Tuto", "recall ComposableRememberUpdateState")
    LaunchedEffect(key1 = Unit) {
        Log.i("Side_effect_Tuto", "before delay")
        delay(2000)
        println("inhere $changeableState")
    }
}

/*
launched
    outside 0
    recall ComposableRememberUpdateState
    before delay
    after function
clicked
    outside 0
    recall ComposableRememberUpdateState
    TODO it's still not working
 */
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SideEffectTheme {
        OrderLaunchedEffect("Android")
    }
}