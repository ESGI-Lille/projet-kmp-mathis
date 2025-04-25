import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val date: String?,
    val imageid: Int,
    val idsid: Int,
    val format: String,
    @SerialName("renditionnumber")
    val renditionNumber: String,
    @SerialName("displayorder")
    val displayOrder: Int,
    val baseimageurl: String,
    val alttext: String?,
    val width: Int,
    val publiccaption: String?,
    val iiifbaseuri: String,
    val height: Int
)