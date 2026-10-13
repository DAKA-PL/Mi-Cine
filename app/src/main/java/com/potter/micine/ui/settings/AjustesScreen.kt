package com.potter.micine.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.potter.micine.ui.theme.MICINETheme

/** Primer avance: diseño visual de Ajustes, sin operaciones de datos ni navegación.
 *  Esta vista solo presenta contenido. Las interacciones se implementarán en futuros commits.
 */
@Composable
fun AjustesScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Mi Cine  ▦",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "♙  Mi Perfil",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            HorizontalDivider()
            Text("Preferencias", style = MaterialTheme.typography.titleLarge)
            AjustePendiente("Sincronizar Datos")
            AjustePendiente("Modo Oscuro")
            Spacer(Modifier.height(6.dp))
            Text("General", style = MaterialTheme.typography.titleLarge)
            AjustePendiente("Idioma")
            AjustePendiente("Notificaciones y Alertas")
            AjustePendiente("Privacidad")
            AjustePendiente("Borrar Historial")
            HorizontalDivider()
            Text(
                text = "Primer avance: estructura de la pantalla. Las opciones se habilitarán en siguientes entregas.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AjustePendiente(titulo: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = titulo,
            modifier = Modifier.weight(1f),
            fontSize = 17.sp,
        )
        Text(
            text = "Próximamente",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AjustesPreview() {
    MICINETheme { AjustesScreen() }
}
