import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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

        var filterValue by remember { mutableStateOf(4) }

        var (rolls, updateRolls) = remember {
            mutableStateOf(
                selectedDice.roll(nbRolls).map { RollVisible(it, RollVisible.superior(it, filterValue)) }
            )
        }



            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                DiceSelector(selectedDice, updateDice)
                rollOptions(nbRolls, updateRolls, selectedDice, filterValue, rolls)
                RollResult(rolls, filterValue)
                DiceDisplay(rolls)
            }


    }
}

@Composable
private fun rollOptions(
    nbRolls: Int,
    updateRolls: (List<RollVisible>) -> Unit,
    selectedDice: Dice,
    filterValue: Int,
    rolls: List<RollVisible>
) {
    var nbRolls1 = nbRolls
    var filterValue1 = filterValue
    Row {
        TextField(
            value = nbRolls1.toString(),
            onValueChange = {
                if (it.isBlank()) {
                    nbRolls1 = 1
                } else {
                    val toIntOrNull = it.toIntOrNull()
                    if (toIntOrNull != null) {
                        nbRolls1 = toIntOrNull
                    }
                }

            },
            label = { Text("Number of Rolls") }
        )
        Button(onClick = {
            reRoll(updateRolls, selectedDice, nbRolls1, filterValue1)
        }) { Text("Roll") }

        TextField(
            value = filterValue1.toString(),
            onValueChange = {
                if (it.isBlank()) {
                    filterValue1 = 1
                } else {
                    val toIntOrNull = it.toIntOrNull()
                    if (toIntOrNull != null) {
                        filterValue1 = toIntOrNull
                    }
                }

                updateRolls(rolls.map { r -> r.copy(visible = r.superior(filterValue1)) })

            },
            label = { Text("KeepOn") }
        )

        Button(onClick = {

            nbRolls1 = rolls.filter { r -> r.superior(filterValue1) }.count()
            reRoll(updateRolls, selectedDice, nbRolls1, filterValue1)
        }) { Text("Reroll") }
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
fun RollResult(rolls: List<RollVisible>, filtreValue: Int) {

    Text("Sum : ${rolls.sumOf { it.value }}")
    Text("Sum Successful roll : ${rolls.filter { it.visible }.sumOf { it.value }}")
    Text("Number of Success ${rolls.filter { it.visible }.size}")
}

@Composable
fun DiceDisplay(rolls: List<RollVisible>) {
    val diceSize = 40.dp
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),

    ) {
        items(rolls.size) { index ->
            val roll = rolls[index]
            Box(
                modifier = Modifier
                    .size(diceSize)
                    .border(
                        width = 4.dp, color = if (roll.visible) Blue else Red,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${roll.value}",
                    textAlign = TextAlign.Center,
                )
                //...
            }

        }
    }

}


@Composable
fun DiceSelector(selectedDice: Dice, updateDice: (Dice) -> Unit) {
    val dices = Dice.entries
    var expanded by remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Dice type ${selectedDice}")
        Box(modifier = Modifier

        ) {

            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }

            ) {
                dices.forEach { dice ->
                    DropdownMenuItem(
                        text = { Text("${dice}") },
                        onClick = { updateDice(dice);expanded = false  })


                }


            }
        }
    }

}

