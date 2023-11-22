package com.kusitms.connectdog.feature.home.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ListForUserItem
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Announcement
import com.kusitms.connectdog.core.util.dateFormat
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.SearchViewModel
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import java.time.LocalDate

private val TAG = "SearchScreen"

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit,
    filterArg: Filter? = Filter(),
    viewModel: SearchViewModel = hiltViewModel(),
    onDetailClick: () -> Unit,
    onNavigateToFilter: (Filter) -> Unit
) {
    viewModel.setFilter(filterArg!!)
    val filter by viewModel.filter.collectAsStateWithLifecycle()
    Log.d(TAG, "filter = $filter")

    val announcementUiState by viewModel.announcementUiState.collectAsStateWithLifecycle()
    val isByDeadline by viewModel.isDeadlineOrder.collectAsStateWithLifecycle()

    Column {
        TopAppBar { onBackClick() }
        SearchBar(
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 6.dp)
                .fillMaxWidth()
        ) {
            onNavigateToFilter(filter)
        }
        if (filter.isNotEmpty()) {
            FilterBar(
                filter = filter,
                onClick = { onNavigateToFilter(filter) }
            )
        }
        AnnouncementContent(
            uiState = announcementUiState,
            sortBtn = {
                SortButton(
                    modifier = Modifier
                        .padding(top = 20.dp, end = 20.dp)
                        .fillMaxWidth(),
                    isByDeadline = isByDeadline
                ) { viewModel.changeOrderCondition() }
            },
            onClick = onDetailClick
        )
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.search_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() }
    )
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(color = Gray7, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp, vertical = 15.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search icon",
            tint = Gray3
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.search_where_to_move),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp,
            color = Gray4
        )
    }
}

@Composable
private fun FilterBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    filter: Filter
) {
    Log.d(TAG, "FilterBar: filter = $filter")
    val dateFilter: String =
        if (filter.startDate != null && filter.endDate != null) {
            dateRangeDisplay(filter.startDate!!, filter.endDate!!)
        } else {
            stringResource(id = R.string.search_location)
        }

    val locationFilter: String =
        if (filter.departure.isNotEmpty() && filter.arrival.isNotEmpty()) {
            filter.departure + " -> " + filter.arrival
        } else {
            stringResource(id = R.string.search_date)
        }

    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .padding(start = 13.dp, end = 13.dp, top = 4.dp, bottom = 6.dp)
            .fillMaxWidth()
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterTag(
            tag = locationFilter,
            isSelected = filter.departure.isNotEmpty()
        ) { onClick() }
        FilterTag(tag = dateFilter, isSelected = filter.startDate != null) { onClick() }
        FilterTag(
            tag = stringResource(id = R.string.search_detail),
            isSelected = filter.detail.isNotEmpty()
        ) { onClick() }
    }
}

@Composable
private fun FilterTag(
    tag: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (isSelected) MaterialTheme.colorScheme.primary else Gray4
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(width = 1.dp, color = color, shape = CircleShape)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 12.sp,
            color = color,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_expand_down),
            contentDescription = "필터 확장",
            tint = color
        )
    }
}

@Composable
private fun SortButton(
    modifier: Modifier = Modifier,
    isByDeadline: Boolean = true,
    onClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.clickable { onClick() }
        ) {
            Text(
                text = if (isByDeadline) {
                    stringResource(id = R.string.search_sort_end)
                } else {
                    stringResource(id = R.string.search_sort_recent)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Gray2
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_sort),
                contentDescription = "정렬",
                tint = Gray2
            )
        }
    }
}

@Composable
private fun AnnouncementContent(
    uiState: AnnouncementUiState,
    sortBtn: @Composable () -> Unit,
    onClick: () -> Unit
) {
    when (uiState) {
        is AnnouncementUiState.Announcements -> {
            AnnouncementList(list = uiState.announcements, sortBtn = sortBtn, onClick = onClick)
        }

        else -> AnnouncementLoading(sortBtn)
    }
}

@Composable
private fun AnnouncementList(
    list: List<Announcement>,
    sortBtn: @Composable () -> Unit,
    onClick: () -> Unit
) {
    LazyColumn {
        item {
            sortBtn()
        }
        items(list) {
            AnnouncementContent(announcement = it, onClick = onClick)
        }
    }
}

@Composable
private fun AnnouncementLoading(
    sortBtn: @Composable () -> Unit
) {
    val list = List(10) {
        Announcement("", "이동봉사 위치", "YY.mm.dd(요일)", "단체이름", false)
    }
    LazyColumn {
        item {
            sortBtn()
        }
        items(list) {
            AnnouncementContent(announcement = it, onClick = {})
        }
    }
}

@Composable
private fun AnnouncementContent(announcement: Announcement, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .clickable { onClick() }
    ) {
        ListForUserItem(
            modifier = Modifier.padding(vertical = 20.dp),
            imageUrl = announcement.imageUrl,
            announcement = announcement
        )
        Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    ConnectDogTheme {
        Column {
            TopAppBar { }
            SearchBar(
                modifier = Modifier
                    .padding(horizontal = 13.dp, vertical = 6.dp)
                    .fillMaxWidth()
            ) {} // todo 검색 popup
            FilterBar(
                modifier = Modifier.padding(start = 13.dp, end = 13.dp, top = 4.dp),
                filter = Filter(),
                onClick = { /*TODO*/ }
            )
            AnnouncementContent(
                uiState = AnnouncementUiState.Loading,
                sortBtn = {
                    SortButton(
                        modifier = Modifier
                            .padding(top = 20.dp, end = 20.dp)
                            .fillMaxWidth(),
                        isByDeadline = true
                    ) { /*todo sort*/ }
                },
                onClick = {}
            )
        }
    }
}

/**
 * UI display
 */
private fun dateRangeDisplay(startDate: LocalDate, endDate: LocalDate): String {
    val datePattern = "M월 dd일"
    if (startDate == endDate) return startDate.dateFormat(datePattern)
    return startDate.dateFormat(datePattern) + " - " + endDate.dateFormat(datePattern)
}
