package app.rickandmorty.data.database.entity

import app.rickandmorty.data.paging.PagedEntry

// @Entity(
//  tableName = "location_paged_entry",
//  indices = [Index(value = ["location_id"])],
//  primaryKeys = ["page", "index"],
//  foreignKeys =
//    [
//      ForeignKey(
//        entity = LocationEntity::class,
//        parentColumns = ["id"],
//        childColumns = ["location_id"],
//        onDelete = ForeignKey.CASCADE,
//      )
//    ],
// )
public data class LocationPagedEntryEntity(
  // @ColumnInfo(name = "page")
  override val page: Int,
  // @ColumnInfo(name = "next_page")
  override val nextPage: Int?,
  // @ColumnInfo(name = "index")
  val index: Int,
  // @ColumnInfo(name = "location_id")
  val locationId: String,
) : PagedEntry
