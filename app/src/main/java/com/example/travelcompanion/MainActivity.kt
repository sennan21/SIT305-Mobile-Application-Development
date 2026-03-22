package com.example.travelcompanion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = appColors) {
                TravelCompanionApp()
            }
        }
    }
}

private val appColors = lightColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = Color(0xFF222222),
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

data class UnitItem(
    val code: String,
    val label: String,
    val group: String
)

enum class AppCategory(val title: String, val units: List<UnitItem>) {
    CURRENCY(
        "Currency",
        listOf(
            UnitItem("USD", "USD", "currency"),
            UnitItem("AUD", "AUD", "currency"),
            UnitItem("EUR", "EUR", "currency"),
            UnitItem("JPY", "JPY", "currency"),
            UnitItem("GBP", "GBP", "currency")
        )
    ),
    FUEL(
        "Fuel",
        listOf(
            UnitItem("MPG", "Miles per Gallon", "efficiency"),
            UnitItem("KM_L", "Kilometers per Liter", "efficiency"),
            UnitItem("GAL", "Gallon (US)", "volume"),
            UnitItem("L", "Liter", "volume"),
            UnitItem("NM", "Nautical Mile", "distance"),
            UnitItem("KM", "Kilometer", "distance")
        )
    ),
    TEMPERATURE(
        "Temperature",
        listOf(
            UnitItem("C", "Celsius", "temperature"),
            UnitItem("F", "Fahrenheit", "temperature"),
            UnitItem("K", "Kelvin", "temperature")
        )
    )
}

@Composable
fun TravelCompanionApp() {
    val context = LocalContext.current
    var selectedCategory by rememberSaveable { mutableStateOf(AppCategory.CURRENCY) }
    var fromCode by rememberSaveable { mutableStateOf(selectedCategory.units.first().code) }
    var toCode by rememberSaveable { mutableStateOf(selectedCategory.units.last().code) }
    var input by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("Result will appear here") }

    val fromUnit = selectedCategory.units.first { it.code == fromCode }
    val availableToUnits = selectedCategory.units.filter { it.group == fromUnit.group }
    val toUnit = availableToUnits.firstOrNull { it.code == toCode } ?: availableToUnits.first()

    fun selectCategory(category: AppCategory) {
        selectedCategory = category
        fromCode = category.units.first().code
        toCode = category.units.last().code
        result = "Result will appear here"
    }

    fun convert() {
        val value = input.toDoubleOrNull()
        when {
            input.isBlank() -> Toast.makeText(context, "Enter a value", Toast.LENGTH_SHORT).show()
            value == null -> Toast.makeText(context, "Enter numbers only", Toast.LENGTH_SHORT).show()
            selectedCategory == AppCategory.FUEL && value < 0 -> {
                Toast.makeText(context, "Fuel values cannot be negative", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val converted = convertValue(selectedCategory, fromUnit, toUnit, value)
                if (converted == null) {
                    Toast.makeText(context, "Select matching units", Toast.LENGTH_SHORT).show()
                } else {
                    result = "${fromUnit.label}: ${formatNumber(value)} -> ${toUnit.label}: ${formatNumber(converted)}"
                }
            }
        }
    }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Travel Companion App", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Choose a category, enter a value, and convert it.", color = Color.DarkGray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppCategory.entries.forEach { category ->
                    Button(
                        onClick = { selectCategory(category) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategory == category) Color.Black else Color(0xFFEDEDED),
                            contentColor = if (selectedCategory == category) Color.White else Color.Black
                        )
                    ) {
                        Text(category.title)
                    }
                }
            }

            SimpleDropdown(
                label = "From",
                selectedLabel = fromUnit.label,
                options = selectedCategory.units,
                onSelected = {
                    fromCode = it.code
                    val updatedToUnits = selectedCategory.units.filter { unit -> unit.group == it.group }
                    if (toCode !in updatedToUnits.map { unit -> unit.code }) {
                        toCode = updatedToUnits.first().code
                    }
                }
            )

            SimpleDropdown(
                label = "To",
                selectedLabel = toUnit.label,
                options = availableToUnits,
                onSelected = { toCode = it.code }
            )

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Value") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(onClick = { convert() }, modifier = Modifier.fillMaxWidth()) {
                Text("Convert")
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3F3F3))
                    .padding(14.dp)
            ) {
                Text(result, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun SimpleDropdown(
    label: String,
    selectedLabel: String,
    options: List<UnitItem>,
    onSelected: (UnitItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label, fontWeight = FontWeight.SemiBold)
        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(selectedLabel)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            onSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun convertValue(category: AppCategory, from: UnitItem, to: UnitItem, value: Double): Double? {
    if (from.group != to.group) return null
    if (from.code == to.code) return value

    return when (category) {
        AppCategory.CURRENCY -> {
            val ratesToUsd = mapOf(
                "USD" to 1.0,
                "AUD" to 1 / 1.55,
                "EUR" to 1 / 0.92,
                "JPY" to 1 / 148.50,
                "GBP" to 1 / 0.78
            )
            val usdValue = value * (ratesToUsd[from.code] ?: return null)
            usdValue / (ratesToUsd[to.code] ?: return null)
        }

        AppCategory.FUEL -> when {
            setOf(from.code, to.code) == setOf("MPG", "KM_L") -> if (from.code == "MPG") value * 0.425 else value / 0.425
            setOf(from.code, to.code) == setOf("GAL", "L") -> if (from.code == "GAL") value * 3.785 else value / 3.785
            setOf(from.code, to.code) == setOf("NM", "KM") -> if (from.code == "NM") value * 1.852 else value / 1.852
            else -> null
        }

        AppCategory.TEMPERATURE -> {
            val celsius = when (from.code) {
                "C" -> value
                "F" -> (value - 32) / 1.8
                "K" -> value - 273.15
                else -> return null
            }

            when (to.code) {
                "C" -> celsius
                "F" -> (celsius * 1.8) + 32
                "K" -> celsius + 273.15
                else -> null
            }
        }
    }
}

fun formatNumber(value: Double): String {
    return if (value % 1.0 == 0.0) {
        value.toInt().toString()
    } else {
        String.format("%.2f", value)
    }
}
