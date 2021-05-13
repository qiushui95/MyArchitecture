package son.ysy.lib.domain.paging

import son.ysy.lib.domain.DomainResult
import son.ysy.lib.entity.PageInfo

data class PagingResult<T>(val pageInfo: PageInfo, val result: DomainResult<List<T>>)
