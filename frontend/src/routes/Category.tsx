import Title from 'components/Category/Title'
import Table from 'components/Category/Table'
import styled from "styled-components"

const Container = styled.section`
  padding: 3vw 5vw;
`

const Category = () => {
  return (
    <Container>
      <Title />
      <Table />
    </Container>
  )
}

export default Category
