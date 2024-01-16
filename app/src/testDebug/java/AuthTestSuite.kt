import org.junit.experimental.categories.Categories
import org.junit.experimental.categories.Categories.IncludeCategory
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.mockito.Mock

@RunWith(Categories::class)
//@IncludeCategory(AuthTests::class)
@SuiteClasses(
) // Note that Categories is a kind of Suite
class AuthTestSuite
{ // Will run Test cases which includes SlowTests but not FastTests

}