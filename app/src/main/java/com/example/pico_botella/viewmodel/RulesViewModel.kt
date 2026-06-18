package com.example.pico_botella.viewmodel

import androidx.lifecycle.ViewModel

/**
 * ViewModel de la ventana "Reglas del juego" (HU 5.0).
 *
 * Esta pantalla no maneja datos de negocio ni persistencia, por lo que no
 * requiere un Repository. Se deja como ViewModel "vacío" siguiendo el patrón
 * MVVM del proyecto: si en el futuro el contenido de las reglas se vuelve
 * dinámico (por ejemplo, viene de un Repository/Room), este es el lugar donde
 * se debe exponer esa información a la vista mediante LiveData.
 */
class RulesViewModel : ViewModel(){
}