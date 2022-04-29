import { Title } from '../../pages/recommendation'
import SubjectTitle from './SubjectTitle'
import Card from '../common/Card'
import _ from 'lodash'
import styled from 'styled-components'

const Container = styled.section`
  margin-bottom: 50px;
`

const Cards = styled.div`
  display: flex;
  justify-content: space-around;
`

type TitleProps = {
  title: Title
}

export default function Subject({ title }: TitleProps) {
  const range = _.range(4)

  return (
    <Container>
      <SubjectTitle>{title.name}</SubjectTitle>
      <Cards>
        {range.map(index => <Card key={index} />)}
      </Cards>
    </Container>
  )
}
