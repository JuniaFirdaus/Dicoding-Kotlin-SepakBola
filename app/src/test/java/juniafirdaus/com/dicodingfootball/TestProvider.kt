package juniafirdaus.com.dicodingfootball

import juniafirdaus.com.dicodingfootball.util.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestProvider : CoroutineContextProvider(){
    override val main: CoroutineContext = Unconfined
}