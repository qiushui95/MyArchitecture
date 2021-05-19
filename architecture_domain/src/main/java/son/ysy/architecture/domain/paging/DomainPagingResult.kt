package son.ysy.architecture.domain.paging

import son.ysy.architecture.domain.DomainResult
import son.ysy.architecture.entity.PageInfo

data class DomainPagingResult<T>(val pageInfo: PageInfo, val result: DomainResult<List<T>>)
