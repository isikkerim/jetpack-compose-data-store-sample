// Gerekli bağımlılıkları import ediyoruz.
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.launch

// DataStore'u Context'e ekliyoruz.
private val Context.dataStore by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    // DataStore erişimini kolaylaştırmak için bir yardımcı sınıf tanımlıyoruz.
    private val userPreferences by lazy {
        UserPreferences(applicationContext.dataStore)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jetpack Compose ile kullanıcı arayüzünü başlatıyoruz.
        setContent {
            Scaffold {
                // Kullanıcı Tercihleri Ekranını çağırıyoruz.
                UserPreferencesScreen(userPreferences)
            }
        }
    }
}

// DataStore işlemleri için bir yardımcı sınıf tanımlıyoruz.

// Kullanıcı Tercihleri Ekranını oluşturuyoruz.
@Composable
fun UserPreferencesScreen(userPreferences: UserPreferences) {
    // Durum yönetimi için değişkenler tanımlıyoruz.
    var username by remember { mutableStateOf("") }
    var newUsername by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // DataStore'dan kullanıcı adını yüklüyoruz.
    LaunchedEffect(Unit) {
        userPreferences.usernameFlow.collect { value ->
            username = value ?: ""
        }
    }

    // Kullanıcı arayüzü düzeni.
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Mevcut kullanıcı adı.
        Text(
            text = "Current Username: $username",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kullanıcı adı girişi.
        TextField(
            value = newUsername,
            onValueChange = { newUsername = it },
            label = { Text("Enter new username") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kaydet butonu.
        Button(onClick = {
            coroutineScope.launch {
                userPreferences.saveUsername(newUsername)
            }
        }) {
            Text("Save Username")
        }
    }
}
