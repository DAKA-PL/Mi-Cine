
package com.potter.micine

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.potter.micine.navigation.MiCineNavGraph
import com.potter.micine.ui.settings.AjustesScreen
import com.potter.micine.ui.settings.AjustesViewModel
import com.potter.micine.ui.theme.MICINETheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val CANAL_ID = "micine_alertas"
        private const val NOTIFICACION_ID = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        crearCanalNotificaciones()

        setContent {

            // VIEWMODEL OBTENIDO CON HILT
            val ajustesViewModel: AjustesViewModel =
                hiltViewModel()

            // ESTADO REACTIVO
            val ajustes = ajustesViewModel.uiState
                .collectAsStateWithLifecycle().value

            // PERMISO PARA NOTIFICACIONES
            val solicitarPermiso =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts
                        .RequestPermission()
                ) { permitido ->

                    ajustesViewModel.cambiarNotificaciones(
                        permitido
                    )

                    if (permitido) {
                        mostrarNotificacion()
                    }
                }

            // TEMA PRINCIPAL
            MICINETheme(
                darkTheme = ajustes.modoOscuro
            ) {

                // NAVEGACIÓN PRINCIPAL
                MiCineNavGraph(
                    ajustesScreen = {

                        AjustesScreen(

                            // MODO OSCURO
                            modoOscuro = ajustes.modoOscuro,

                            onModoOscuroChange = {
                                ajustesViewModel
                                    .cambiarModoOscuro(it)
                            },

                            // IDIOMA
                            idioma = ajustes.idioma,

                            onIdiomaChange = {
                                ajustesViewModel
                                    .cambiarIdioma(it)
                            },

                            // NOTIFICACIONES
                            notificaciones =
                                ajustes.notificaciones &&
                                        tienePermisoNotificaciones(),

                            onNotificacionesChange = { activado ->

                                if (
                                    activado &&
                                    Build.VERSION.SDK_INT >=
                                    Build.VERSION_CODES.TIRAMISU &&
                                    !tienePermisoNotificaciones()
                                ) {

                                    solicitarPermiso.launch(
                                        Manifest.permission
                                            .POST_NOTIFICATIONS
                                    )

                                } else {

                                    ajustesViewModel
                                        .cambiarNotificaciones(
                                            activado
                                        )

                                    if (activado) {

                                        mostrarNotificacion()

                                    } else {

                                        val manager =
                                            getSystemService(
                                                Context
                                                    .NOTIFICATION_SERVICE
                                            ) as NotificationManager

                                        manager.cancel(
                                            NOTIFICACION_ID
                                        )
                                    }
                                }
                            },

                            // BORRAR HISTORIAL
                            // Pendiente de integrar con Room
                            onBorrarHistorial = {
                                // Se conectará posteriormente
                            }
                        )
                    }
                )
            }
        }
    }

    // VERIFICAR PERMISO
    private fun tienePermisoNotificaciones(): Boolean {

        return if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

        } else {
            true
        }
    }

    // CREAR CANAL DE NOTIFICACIONES
    private fun crearCanalNotificaciones() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            val canal = NotificationChannel(
                CANAL_ID,
                "Notificaciones Mi Cine",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description =
                    "Avisos y alertas de la aplicación Mi Cine"
            }

            val manager = getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

            manager.createNotificationChannel(canal)
        }
    }

    // MOSTRAR NOTIFICACIÓN DE PRUEBA
    @Suppress("DEPRECATION")
    private fun mostrarNotificacion() {

        if (!tienePermisoNotificaciones()) {
            return
        }

        val builder = if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {
            Notification.Builder(this, CANAL_ID)
        } else {
            Notification.Builder(this)
        }

        val notificacion = builder
            .setSmallIcon(
                android.R.drawable.ic_dialog_info
            )
            .setContentTitle("Mi Cine")
            .setContentText(
                "¡Notificaciones activadas correctamente!"
            )
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        manager.notify(
            NOTIFICACION_ID,
            notificacion
        )
    }
}
