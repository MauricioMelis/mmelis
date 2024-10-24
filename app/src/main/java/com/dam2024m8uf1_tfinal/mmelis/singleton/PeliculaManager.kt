import android.util.Log
import com.dam2024m8uf1_tfinal.mmelis.Pelicula

object PeliculaManager {
 var pelicula: Pelicula? = null

 fun crearPelicula(pelicula: Pelicula) {
  this.pelicula = pelicula
  Log.i("PeliculaManager", "Película creada: $pelicula")
 }

 fun editarPelicula(nuevaPelicula: Pelicula) {
  this.pelicula = nuevaPelicula
  Log.i("PeliculaManager", "Película editada: $nuevaPelicula")
 }

 fun obtenerPelicula(): Pelicula? {
  return pelicula
 }

 fun eliminarPelicula() {
  pelicula = null
  Log.i("PeliculaManager", "Película eliminada")
 }
}
