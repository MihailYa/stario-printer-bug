package com.mikleya.starioprinterbug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.StarDeviceDiscoveryManager
import com.starmicronics.stario10.StarDeviceDiscoveryManagerFactory
import com.starmicronics.stario10.StarPrinter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    launchSearchPrinters()
  }

  private fun launchSearchPrinters() = lifecycleScope.launch(Dispatchers.Default) {
    repeat(1000) {
      Log.d(TAG, "Try to reproduce ConcurrentModificationException bug #$it")

      val job1 = launch {
        searchPrinters()
      }

      delay(300)
      job1.cancel()
      delay(500)
    }
  }

  private suspend fun searchPrinters(): List<StarPrinter> {
    val manager = StarDeviceDiscoveryManagerFactory.create(
      interfaceTypes = listOf(InterfaceType.Lan, InterfaceType.Usb),
      context = applicationContext
    )
    manager.discoveryTime = 5000

    return suspendCoroutine { cont ->
      val printers = mutableListOf<StarPrinter>()

      manager.callback = object : StarDeviceDiscoveryManager.Callback {
        override fun onPrinterFound(printer: StarPrinter) {
          printers.add(printer)
        }

        override fun onDiscoveryFinished() {
          cont.resume(printers.toList())
        }
      }

      manager.startDiscovery()
    }
  }

  companion object {
      private const val TAG = "MainActivity"
  }
}