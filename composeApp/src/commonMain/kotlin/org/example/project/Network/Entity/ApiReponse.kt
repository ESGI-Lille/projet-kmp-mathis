import kotlinx.serialization.Serializable
import org.example.project.Network.Entity.Info

@Serializable
data class ApiResponseObject(
    val info: Info,
    val records: List<Record>,
)

@Serializable
data class ApiReponsePerson(
    val id: Int,
    val displayname: String,
    val culture: String,
    val gender: String,
    val objectcount: Int,
    val names: List<Name>
)


@Serializable
data class Name(
    val displayname: String,
    val type: String
)