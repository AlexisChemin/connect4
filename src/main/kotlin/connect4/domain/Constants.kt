package connect4.domain


enum class RowIndex {
    ROW_0,
    ROW_1,
    ROW_2,
    ROW_3,
    ROW_4,
    ROW_5,
}

enum class ColumnIndex {

    COLUMN_0,
    COLUMN_1,
    COLUMN_2,
    COLUMN_3,
    COLUMN_4,
    COLUMN_5,
    COLUMN_6,
}

@JvmField val ColumnHeight = RowIndex.values().size

@JvmField val GridWidth = ColumnIndex.values().size
