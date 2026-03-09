package com.takethistoyourgrave.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.takethistoyourgrave.todos.navigation.ToDosNavigation
import com.takethistoyourgrave.todos.ui.theme.ToDosTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ToDosTheme {
                ToDosNavigation()
            }
        }
    }
}
