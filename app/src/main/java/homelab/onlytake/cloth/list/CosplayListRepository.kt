package homelab.onlytake.cloth.list

import android.graphics.BitmapFactory
import androidx.camera.core.processing.SurfaceProcessorNode.In
import homelab.onlytake.database.Cloth
import homelab.onlytake.database.MyDao

class CosplayListRepository(private val dao: MyDao) {

    suspend fun getHeaderListData(): List<HeaderListData> {
        val genres = dao.getAllGenres()
        val headerListData = mutableListOf<HeaderListData>()

        for (genre in genres) {
            val clothes = dao.getClothesByGenre(genre.id)

            val items = clothes.map { cloth ->
                CosplayData(
                    id = cloth.id,
                    bitmap = BitmapFactory.decodeFile(cloth.picturePath),
                    title = cloth.name
                )
            }

            headerListData.add(HeaderListData(header = genre.name, items = items))
        }

        return headerListData
    }

    suspend fun deleteCloth(id: Int) {
        dao.deleteClothById(id)
    }
}