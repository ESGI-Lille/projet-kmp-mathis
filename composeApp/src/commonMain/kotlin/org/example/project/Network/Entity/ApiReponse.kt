import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.example.project.Network.Entity.Movie

@Serializable
data class ApiResponseObject(
    val page: Int? = null,
    val results : List<Movie>? = null,
    val total_pages : Int? = null
)






