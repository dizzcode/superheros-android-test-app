package dizzcode.com.superheros

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dizzcode.com.superheros.ui.theme.SuperheroesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperheroesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SuperheroesApp()
                }
            }
        }
    }
}

@Composable
fun SuperheroesApp(modifier: Modifier = Modifier) {

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SuperheroesAppDarkThemePreview() {
    SuperheroesTheme( darkTheme = true){
        SuperheroesApp()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SuperheroesAppPreview() {
    SuperheroesTheme {
        SuperheroesApp()
    }
}
