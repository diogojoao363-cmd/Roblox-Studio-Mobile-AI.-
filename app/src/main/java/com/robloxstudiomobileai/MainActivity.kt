package com.robloxstudiomobileai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF0B1020)
private val Panel = Color(0xFF131C30)
private val Panel2 = Color(0xFF1A2740)
private val Accent = Color(0xFF36B8F5)
private val Muted = Color(0xFF91A4C2)
private val White = Color(0xFFEAF3FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StudioMobileApp() }
    }
}

@Composable
fun StudioMobileApp() {
    var selectedTab by remember { mutableStateOf("Editor") }
    var selectedObject by remember { mutableStateOf("Workspace") }
    var script by remember {
        mutableStateOf("-- Seu script Luau\nprint(\"Olá, Roblox!\")\n")
    }
    var output by remember { mutableStateOf("Pronto. Nenhum teste foi executado.") }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Bg) {
            Column(
                modifier = Modifier.fillMaxSize().padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Header()
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TopTab("Editor", selectedTab == "Editor") { selectedTab = "Editor" }
                    TopTab("Explorer", selectedTab == "Explorer") { selectedTab = "Explorer" }
                    TopTab("Propriedades", selectedTab == "Propriedades") { selectedTab = "Propriedades" }
                }
                when (selectedTab) {
                    "Explorer" -> ExplorerPanel(selectedObject) { selectedObject = it }
                    "Propriedades" -> PropertiesPanel(selectedObject)
                    else -> {
                        ViewportPanel()
                        EditorPanel(script, onScriptChange = { script = it })
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { output = "Editor local pronto. A execução Luau ainda não está integrada." },
                                colors = ButtonDefaults.buttonColors(containerColor = Accent)
                            ) {
                                androidx.compose.material3.Icon(Icons.Default.PlayArrow, null)
                                Spacer(Modifier.width(5.dp))
                                Text("Verificar")
                            }
                            Button(
                                onClick = { output = "Painel de saída atualizado." },
                                colors = ButtonDefaults.buttonColors(containerColor = Panel2)
                            ) {
                                androidx.compose.material3.Icon(Icons.Default.Terminal, null)
                                Spacer(Modifier.width(5.dp))
                                Text("Output")
                            }
                        }
                        OutputPanel(output)
                    }
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "ROBLOX STUDIO MOBILE AI • protótipo 0.1.0",
                    color = Muted,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("STUDIO MOBILE", color = Accent, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Text("Novo projeto", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Box(
            modifier = Modifier.size(42.dp).background(Panel2, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Icon(Icons.Default.Settings, null, tint = Accent)
        }
    }
}

@Composable
private fun TopTab(label: String, active: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(if (active) Accent.copy(alpha = 0.18f) else Panel, RoundedCornerShape(10.dp))
            .border(1.dp, if (active) Accent else Panel2, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 9.dp)
    ) {
        Text(label, color = if (active) Accent else Muted, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ViewportPanel() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .height(180.dp)
            .background(Panel, RoundedCornerShape(14.dp))
            .border(1.dp, Panel2, RoundedCornerShape(14.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("VISUALIZAÇÃO DO PROJETO", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Text("3D • EM DESENVOLVIMENTO", color = Accent, fontSize = 9.sp)
        }
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f)
                .background(Color(0xFF0D1729), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("◇", color = Accent, fontSize = 34.sp)
                Text("Área de cena 3D", color = White, fontWeight = FontWeight.SemiBold)
                Text("O motor de construção será integrado nesta etapa.", color = Muted, fontSize = 10.sp)
            }
        }
    }
}

@Composable
private fun EditorPanel(value: String, onScriptChange: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .height(190.dp)
            .background(Panel, RoundedCornerShape(14.dp))
            .border(1.dp, Panel2, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Icon(Icons.Default.Code, null, tint = Accent, modifier = Modifier.size(17.dp))
            Spacer(Modifier.width(7.dp))
            Text("Script.luau", color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.weight(1f))
            Text("LUAU", color = Accent, fontSize = 10.sp)
        }
        Spacer(Modifier.height(10.dp))
        BasicTextField(
            value = value,
            onValueChange = onScriptChange,
            modifier = Modifier.fillMaxSize(),
            textStyle = androidx.compose.ui.text.TextStyle(
                color = Color(0xFFC8D8F0),
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                lineHeight = 18.sp
            ),
            decorationBox = { inner ->
                Box {
                    if (value.isEmpty()) Text("Digite seu código Luau...", color = Muted, fontSize = 12.sp)
                    inner()
                }
            }
        )
    }
}

@Composable
private fun ExplorerPanel(selected: String, onSelect: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(14.dp)).padding(14.dp)
    ) {
        Text("EXPLORER", color = Accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(Modifier.height(10.dp))
        listOf("Workspace", "  Camera", "  Terrain", "  Part", "Players", "Lighting", "ReplicatedStorage", "ServerScriptService", "  Script").forEach {
            Text(
                text = "▸  $it",
                color = if (selected == it.trim()) Accent else White,
                modifier = Modifier.fillMaxWidth().clickable { onSelect(it.trim()) }.padding(vertical = 8.dp),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun PropertiesPanel(selected: String) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(14.dp)).padding(14.dp)
    ) {
        Text("PROPRIEDADES", color = Accent, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Spacer(Modifier.height(12.dp))
        Text("Objeto selecionado", color = Muted, fontSize = 11.sp)
        Text(selected, color = White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        listOf("Name" to selected, "ClassName" to "Instance", "Position" to "0, 0, 0", "Anchored" to "false").forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(key, color = Muted, fontSize = 12.sp)
                Text(value, color = White, fontSize = 12.sp)
            }
        }
        Text("Edição de propriedades será ligada ao modelo de cena na próxima etapa.", color = Muted, fontSize = 10.sp)
    }
}

@Composable
private fun OutputPanel(message: String) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color(0xFF080D18), RoundedCornerShape(12.dp)).padding(10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            androidx.compose.material3.Icon(Icons.Default.Terminal, null, tint = Accent, modifier = Modifier.size(15.dp))
            Spacer(Modifier.width(6.dp))
            Text("OUTPUT", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(6.dp))
        Text(message, color = Color(0xFFB9C9DE), fontSize = 11.sp)
    }
}
