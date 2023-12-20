package com.shadow_shift_studio.travelagency_frontend.domain.use_case

import com.shadow_shift_studio.travelagency_frontend.domain.repository.IViewModelRepository

class ViewModelUseCase(): IViewModelRepository {
    override fun removeTrailingSpaces(input: String): String {
        return input.trimEnd { it.isWhitespace() }
    }
}