import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Record(
    @SerialName("createdate")
    val createDate: String,
    val division: String,
    val id: Int,
    val period: String?,
    val images: List<Image>,
    val title: String,
    val dated: String?,
    val url: String,
    val century: String?,
    val culture: String?
)