import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import diceroller.composeapp.generated.resources.Res
import diceroller.composeapp.generated.resources.compose_multiplatform


@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var (selectedDice ,updateDice) = remember { mutableStateOf(Dice.SIX) }
        a()

        PermanentNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {

                    Column {
//                        Button(onClick = { println("machin") }) { Text("truc") }
//                        Button(onClick = { println("machin2") }) { Text("truc2") }
//                        Button(onClick = { println("machin3") }) { Text("truc3") }
                        DiceSelector(selectedDice ,updateDice)
                    }
                }
            },
        ) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {

                    Text("Selected $selectedDice")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember {"machin"}
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                        LeftMenu()
                    }
                }
            }

        }
    }
}

@Composable
fun LeftMenu() {
    Column {
        Button(onClick = { println("machin") }) {
            Text("Left")
        }
    }
}

@Composable
fun a() {



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

@Composable
fun Demo_DropDownMenu() {

    var expanded by remember { mutableStateOf(true) }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Load") },
            onClick = { println("click load") }
        )
        DropdownMenuItem(
            text = { Text("Save") },
            onClick = { println("click save") }
        )
    }
//
//    Box(
//        modifier = Modifier.fillMaxWidth()
//            .wrapContentSize(Alignment.TopEnd)
//    ) {
//        IconButton(onClick = { expanded = !expanded }) {
//            Icon(
//                imageVector = Icons.Default.MoreVert,
//                contentDescription = "More"
//            )
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            DropdownMenuItem(
//                text = { Text("Load") },
//                onClick = { println("click load") }
//            )
//            DropdownMenuItem(
//                text = { Text("Save") },
//                onClick = { println("click save") }
//            )
//        }
//    }
}
