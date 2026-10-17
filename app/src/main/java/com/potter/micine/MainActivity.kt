
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.potter.micine.ui.settings.AjustesScreen
import com.potter.micine.ui.theme.MICINETheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val CANAL_ID = "micine_alertas"
        private const val NOTIFICACION_ID = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Guardar preferencias en el dispositivo
        val preferencias = getSharedPreferences(
            "MiCinePreferencias",
            MODE_PRIVATE
        )

        // Crear canal de notificaciones
        crearCanalNotificaciones()

        setContent {

            // MODO OSCURO
            var modoOscuro by rememberSaveable {
                mutableStateOf(
                    preferencias.getBoolean("modoOscuro", false)
                )
            }

            // IDIOMA
            var idioma by rememberSaveable {
                mutableStateOf(
                    preferencias.getString("idioma", "Español")
                        ?: "Español"
                )
            }

            // NOTIFICACIONES Y ALERTAS
            var notificaciones by rememberSaveable {
                mutableStateOf(
                    preferencias.getBoolean(
                        "notificaciones",
                        false
                    ) && tienePermisoNotificaciones()
                )
            }

            // SOLICITAR PERMISO PARA NOTIFICACIONES
            val solicitarPermiso =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts
                        .RequestPermission()
                ) { permitido ->

                    notificaciones = permitido

                    preferencias.edit()
                        .putBoolean(
                            "notificaciones",
                            permitido
                        )
                        .apply()

                    if (permitido) {
                        mostrarNotificacion()
                    }
                }

            // TEMA DE LA APLICACIÓN
            MICINETheme(darkTheme = modoOscuro) {

                AjustesScreen(

                    // MODO OSCURO
                    modoOscuro = modoOscuro,

                    onModoOscuroChange = { activado ->

                        modoOscuro = activado

                        preferencias.edit()
                            .putBoolean(
                                "modoOscuro",
                                activado
                            )
                            .apply()
                    },

                    // IDIOMA
                    idioma = idioma,

                    onIdiomaChange = { nuevoIdioma ->

                        idioma = nuevoIdioma

                        preferencias.edit()
                            .putString(
                                "idioma",
                                nuevoIdioma
                            )
                            .apply()
                    },

                    // NOTIFICACIONES Y ALERTAS
                    notificaciones = notificaciones,

                    onNotificacionesChange = { activado ->

                        if (activado) {

                            // Verificar permiso en Android 13+
                            if (
                                Build.VERSION.SDK_INT >=
                                Build.VERSION_CODES.TIRAMISU &&
                                !tienePermisoNotificaciones()
                            ) {

                                solicitarPermiso.launch(
                                    Manifest.permission
                                        .POST_NOTIFICATIONS
                                )

                            } else {

                                // Activar notificaciones
                                notificaciones = true

                                preferencias.edit()
                                    .putBoolean(
                                        "notificaciones",
                                        true
                                    )
                                    .apply()

                                // Enviar notificación de prueba
                                mostrarNotificacion()
                            }

                        } else {

                            // Desactivar notificaciones
                            notificaciones = false

                            preferencias.edit()
                                .putBoolean(
                                    "notificaciones",
                                    false
                                )
                                .apply()

                            // Cancelar notificación de prueba
                            val manager = getSystemService(
                                Context.NOTIFICATION_SERVICE
                            ) as NotificationManager

                            manager.cancel(NOTIFICACION_ID)
                        }
                    },

                    // BORRAR HISTORIAL
                    // Pendiente de conexión con Room
                    onBorrarHistorial = {
                        // Tu compañero conectará aquí
                        // la función que elimina el historial.
                    }
                )
            }
        }
    }

    // VERIFICAR PERMISO DE NOTIFICACIONES
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

        // Evitar notificar sin permiso
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
