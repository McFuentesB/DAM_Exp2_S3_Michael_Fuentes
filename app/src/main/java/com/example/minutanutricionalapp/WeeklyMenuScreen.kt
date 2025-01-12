package com.example.minutanutricionalapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun WeeklyMenuScreen(navController: NavController) {
    // Datos de ejemplo para las recetas semanales
    val recipes = listOf(
        Recipe("Ensalada de Quinoa", "Una receta deliciosa y saludable con quinoa, vegetales frescos y aderezos ligeros.", "https://comedera.com/wp-content/uploads/sites/9/2015/10/ensalada-de-pollo.jpg?resize=1316,740&quality=80", "Lunes"),
        Recipe("Tacos de Pollo", "Tacos de pollo con salsa casera, guacamole y más.", "https://www.hola.com/horizon/landscape/e94427fe9d41-pastor-pollo-adobe-t.jpg?im=Resize=(960),type=downsize", "Martes"),
        Recipe("Sopa de Lentejas", "Sopa rica en proteínas y fibra, ideal para una comida reconfortante.", "https://www.hola.com/horizon/landscape/1315297d04df-0373b8ae049d-lentejas-verduras-adobe-t.jpg?im=Resize=(960),type=downsize", "Miércoles"),
        Recipe("Pasta con Tomate y Albahaca", "Pasta fresca con una salsa de tomate natural y albahaca.", "https://www.hola.com/horizon/landscape/b04c1f873e4a-tornillos-gremolata-adobe-t.jpg?im=Resize=(960),type=downsize", "Jueves"),
        Recipe("Pollo al Horno", "Pollo jugoso al horno con papas y especias.", "https://www.hola.com/horizon/landscape/7307343a2f72-adobestock734541868.jpg?im=Resize=(960),type=downsize", "Viernes")
    )

    @Composable
    fun ScreenTitle() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(8.dp))

            // Título con gradiente
            Text(
                text = "Minuta Nutricional Semanal",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    @Composable
    fun LogoutButton(navController: NavController) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Navegar a la pantalla de login al cerrar sesión
                navController.navigate("login_screen") {
                    popUpTo("login_screen") { inclusive = true } // Elimina las pantallas anteriores de la pila
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Altura estándar para botones prominentes
            shape = RoundedCornerShape(28.dp), // Bordes redondeados
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            // Fila para alinear el icono y el texto
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar Sesión",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }

    // Diseño de la pantalla de recetas semanales
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título de la pantalla
        ScreenTitle()

        // Mostrar las recetas en una lista
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(recipes.size) { index ->
                RecipeCard(recipe = recipes[index])
            }
        }

        // Botón de cerrar sesión
        LogoutButton(navController = navController)
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Caja para la imagen y el día de la semana
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                            )
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                // Imagen de la receta desde la URL
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(recipe.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = recipe.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    alignment = Alignment.Center
                )

                // Día de la semana sobre la imagen
                Text(
                    text = recipe.dia.uppercase(), // Día de la semana
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre de la receta
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Descripción de la receta
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Datos de ejemplo para las recetas
data class Recipe(val name: String, val description: String, val imageUrl: String, val dia: String)

@Preview(showBackground = true)
@Composable
fun WeeklyMenuPreview() {
    WeeklyMenuScreen(navController = NavController(LocalContext.current)) // Se puede usar un navController de prueba
}
