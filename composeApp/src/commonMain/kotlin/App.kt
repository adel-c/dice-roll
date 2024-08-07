import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import diceroller.composeapp.generated.resources.Res
import diceroller.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var (selectedDice ,updateDice) = remember { mutableStateOf(Dice.SIX) }
        var nbRolls by remember { mutableStateOf(1) }
        var rolls by remember { mutableStateOf(listOf<Int>()) }

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
                        Button(onClick = {rolls = selectedDice.roll(nbRolls)  }) { Text("Roll") }
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


@Composable
fun RollResult(rolls:List<Int>) {

    Column {
        rolls.forEach { roll ->
            Row{
                Text(" $roll")
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

