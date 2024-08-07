import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

data class RollVisible(val value: Int, val visible: Boolean) {
    companion object {
        fun superior(value: Int, v: Int): Boolean = value >= v;
    }

    fun superior(v: Int): Boolean = superior(value, v)
}
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var (selectedDice ,updateDice) = remember { mutableStateOf(Dice.SIX) }
        var nbRolls by remember { mutableStateOf(6) }
        var (rolls, updateRolls) = remember { mutableStateOf(listOf<RollVisible>()) }
        var filterValue by remember { mutableStateOf(4) }
        PermanentNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {

                    Column {

                        DiceSelector(selectedDice ,updateDice)

                        TextField(
                            value = nbRolls.toString(),
                            onValueChange = {
                                if (it.isBlank()) {
                                    nbRolls = 1
                                } else {
                                    val toIntOrNull = it.toIntOrNull()
                                    if (toIntOrNull != null) {
                                        nbRolls = toIntOrNull
                                    }
                                }

                            },
                            label = { Text("Number of Rolls") }
                        )
                        Button(onClick = {
                            reRoll(updateRolls, selectedDice, nbRolls,filterValue)
                        }) { Text("Roll") }

                        TextField(
                            value = filterValue.toString(),
                            onValueChange = {
                                if (it.isBlank()) {
                                    filterValue = 1
                                } else {
                                    val toIntOrNull = it.toIntOrNull()
                                    if (toIntOrNull != null) {
                                        filterValue = toIntOrNull
                                    }
                                }

                                updateRolls(rolls.map { r -> r.copy(visible = r.superior( filterValue)) })

                            },
                            label = { Text("KeepOn") }
                        )

                        Button(onClick = {

                        nbRolls = rolls.filter { r -> r.superior(filterValue)}.count()
                            reRoll(updateRolls, selectedDice, nbRolls, filterValue)
                        }) { Text("Reroll") }

                    }
                }
            },
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                RollResult(rolls)
            }

        }
    }
}

private fun reRoll(
    updateRolls: (List<RollVisible>) -> Unit,
    selectedDice: Dice,
    nbRolls: Int,
    filtreValue: Int,
) {
    updateRolls(selectedDice.roll(nbRolls).map { RollVisible(it, RollVisible.superior(it,filtreValue)) })
}


@Composable
fun RollResult(rolls: List<RollVisible>) {

    Column {
        rolls.forEach { roll ->
            Row{
                Text(" ${roll.value} ${roll.visible}")
            }
        }

    }
}


@Composable
fun DiceSelector(selectedDice: Dice, updateDice: (Dice) -> Unit) {
    val dices = Dice.entries

    Column {
        dices.forEach { dice ->
            Row{
                Button(onClick = { updateDice( dice) }) { Text(dice.name) }
            }
        }
        Text("Selected $selectedDice")
    }
}

