package son.ysy.lib.domain.paging

import son.ysy.lib.entity.ModelResult
import son.ysy.lib.entity.PageInfo

data class PagingResult<T>(val pageInfo: PageInfo, val result: ModelResult<T>)
