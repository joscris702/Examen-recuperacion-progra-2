package com.example.roomcronoapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roomcronoapp.R
import com.example.roomcronoapp.component.CircleButton
import com.example.roomcronoapp.component.MainIconButton
import com.example.roomcronoapp.component.MainTextField
import com.example.roomcronoapp.component.MainTitle
import com.example.roomcronoapp.component.formatTiempo
import com.example.roomcronoapp.model.Cronos
import com.example.roomcronoapp.viewModels.CronometroViewModel
import com.example.roomcronoapp.viewModels.CronosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
    navController: NavController,
    cronometroVM: CronometroViewModel,
    cronosVM: CronosViewModel,
    id: Long
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { MainTitle(title = "EDIT CRONO") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) { it ->
        ContentEditView(it, navController, cronometroVM, cronosVM, id)
    }
}

@Composable
fun ContentEditView(
    it: PaddingValues,
    navController: NavController,
    cronometroVM: CronometroViewModel,
    cronosVM: CronosViewModel,
    id:Long
) {

    val state = cronometroVM.state

    LaunchedEffect(state.cronometroActivo) {
        cronometroVM.cronos()
    }

    //consultamos en base de datos el crono por ID
    LaunchedEffect(Unit){
        cronometroVM.getCronoById(id)
    }

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = formatTiempo(cronometroVM.tiempo),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            // iniciar
            CircleButton(
                icon = painterResource(id = R.drawable.play),
                enabled = !state.cronometroActivo
            ) {
                cronometroVM.iniciar()
            }
            // pausar
            CircleButton(
                icon = painterResource(id = R.drawable.pause),
                enabled = state.cronometroActivo
            ) {
                cronometroVM.pausar()
            }
        }
        MainTextField(
            value = state.title,
            onValueChange = { cronometroVM.onValue(it) },
            label = "Title"
        )

        Button(onClick = {
            cronosVM.updateCrono(
                Cronos(
                    id = id,
                    title = state.title,
                    crono = cronometroVM.tiempo
                )
            )
            //regresar a la vista de home
            navController.popBackStack()

        }) {
            Text(text = "Editar")
        }
        //este bloque se ejecutara al salir de la vista, es decir
        //al salir de edit vamos invocar a detener el cronometro
        DisposableEffect(Unit){
            onDispose {
                cronometroVM.detener()
            }
        }

    }
}