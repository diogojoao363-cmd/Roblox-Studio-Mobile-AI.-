package com.robloxstudiomobileai

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import org.json.JSONObject

private val Bg = Color(0xFF0A1020)
private val Panel = Color(0xFF151F33)
private val Accent = Color(0xFF36B8F5)
private val White = Color(0xFFEAF3FF)
private val Muted = Color(0xFF91A4C2)

class MainActivity : ComponentActivity() {
    private var studioWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StudioMobileApp(::runSceneCommand, ::openCreatorHub) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    private fun StudioMobileApp(runCommand: (String) -> Unit, openHub: () -> Unit) {
        var prompt by remember { mutableStateOf("") }
        var output by remember { mutableStateOf("Motor 3D iniciando. A primeira abertura precisa de internet para carregar o motor.") }
        var explorer by remember { mutableStateOf("Workspace") }
        var script by remember { mutableStateOf("-- Escreva seu script Luau aqui\nprint(\"Olá, Roblox!\")") }

        MaterialTheme {
            Surface(Modifier.fillMaxSize(), color = Bg) {
                Column(Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("ROBLOX STUDIO MOBILE AI", color = Accent, fontSize = 11.sp)
                            Text("Editor 3D • versão 0.3.0", color = White, fontSize = 18.sp)
                        }
                        TextButton(onClick = openHub) { Text("CREATOR HUB ↗", color = Accent, fontSize = 11.sp) }
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        ToolButton("Bloco") { runCommand("part:cube") }
                        ToolButton("Esfera") { runCommand("part:sphere") }
                        ToolButton("Cilindro") { runCommand("part:cylinder") }
                        ToolButton("Piso") { runCommand("part:floor") }
                    }

                    AndroidView(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        factory = { context ->
                            WebView(context).apply {
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.allowFileAccess = true
                                settings.allowContentAccess = true
                                settings.javaScriptCanOpenWindowsAutomatically = false
                                webChromeClient = WebChromeClient()
                                webViewClient = object : WebViewClient() {
                                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean = true
                                    override fun onPageFinished(view: WebView, url: String) {
                                        output = "Cena 3D carregada. Arraste para girar e use dois dedos para aproximar."
                                    }
                                }
                                addJavascriptInterface(object {
                                    @JavascriptInterface
                                    fun report(message: String) {
                                        runOnUiThread { output = message }
                                    }
                                    @JavascriptInterface
                                    fun selected(name: String) {
                                        runOnUiThread { explorer = "Workspace  ›  $name" }
                                    }
                                }, "AndroidStudio")
                                studioWebView = this
                                loadUrl("file:///android_asset/studio3d.html")
                            }
                        },
                        update = { studioWebView = it }
                    )

                    Text("TOOLBOX • PEÇAS BÁSICAS", color = Accent, fontSize = 10.sp)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        ToolButton("Bloco") { runCommand("part:cube") }
                        ToolButton("Esfera") { runCommand("part:sphere") }
                        ToolButton("Cilindro") { runCommand("part:cylinder") }
                        ToolButton("Parede") { runCommand("part:wall") }
                    }

                    Text("IA CONSTRUTORA", color = Accent, fontSize = 10.sp)
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        BasicTextField(
                            value = prompt,
                            onValueChange = { prompt = it },
                            modifier = Modifier.weight(1f).background(Panel, RoundedCornerShape(10.dp)).padding(12.dp),
                            textStyle = androidx.compose.ui.text.TextStyle(color = White, fontSize = 13.sp),
                            decorationBox = { inner ->
                                Box {
                                    if (prompt.isEmpty()) Text("Ex.: crie um labirinto grande", color = Muted, fontSize = 12.sp)
                                    inner()
                                }
                            }
                        )
                        Spacer(Modifier.width(6.dp))
                        Button(
                            onClick = {
                                if (prompt.isNotBlank()) {
                                    runCommand("ai:" + prompt)
                                    prompt = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Accent),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
                        ) { Text("CRIAR", color = Bg, fontSize = 11.sp) }
                    }

                    Text(explorer, color = Muted, fontSize = 10.sp)
                    Text(output, color = White, fontSize = 10.sp, modifier = Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(8.dp)).padding(8.dp))
                }
            }
        }
    }

    private fun runSceneCommand(command: String) {
        val safe = JSONObject.quote(command)
        studioWebView?.evaluateJavascript("window.mobileStudioCommand($safe)", null)
    }

    private fun openCreatorHub() {
        runCatching {
            startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://create.roblox.com")))
        }
    }

    @Composable
    private fun ToolButton(label: String, action: () -> Unit) {
        Button(
            onClick = action,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Panel),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
        ) { Text(label, color = White, fontSize = 10.sp) }
    }
}
