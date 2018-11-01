package juniafirdaus.com.dicodingfootball.api

import org.junit.Test

import org.mockito.Mockito

class ApiRepositoryTest {

    //test koneksi ke api
    @Test
    fun doRequest() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }
}