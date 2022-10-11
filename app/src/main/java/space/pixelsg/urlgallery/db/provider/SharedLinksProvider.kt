package space.pixelsg.urlgallery.db.provider

import space.pixelsg.urlgallery.db.dao.SharedLinkDao
import space.pixelsg.urlgallery.db.model.SharedLink
import javax.inject.Inject

class SharedLinksProvider @Inject constructor(private val sharedLinkDao: SharedLinkDao) {
    fun getSharedLink(id: Long) = sharedLinkDao.getById(id)
    fun addSharedLink(id: Long, link: String) = sharedLinkDao.insert(SharedLink(id, link))
}