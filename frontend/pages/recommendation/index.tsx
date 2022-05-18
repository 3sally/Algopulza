import { ReactElement, useState, useEffect } from 'react'
import Layout from '../../components/common/Layout'
import Gift from '../../components/random/Gift'
import Subject from '../../components/recommendation/Subject'
import styled from 'styled-components'
import { NextSeo } from 'next-seo';

import { useRecoilValue } from 'recoil'
import { bojIdState, accessTokenState, algoIdState } from '../../util/stateCollection'
import { getRecoVul } from '../../api/flask/recommend/RecoVul'
import { getRecoTag } from '../../api/flask/recommend/RecoTag'
import { getSolvedTear } from '../../api/flask/recommend/RecoSolvedTear'
import { getRecoTear } from '../../api/flask/recommend/RecoTear'
import { CircularProgress } from "@mui/material"
import ButtonFloating from '../../components/common/button/ButtonFloating'

const Container = styled.section`
  padding: 0vh 5vw;
`

const CircularProgressContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 40vh;
  `

const SubContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`

export default function Recommendation() {
  const [alert, setAlert ] = useState(true)
  const [vulData, setVulData] = useState()
  const [tagData, setTagData] = useState()
  const [solvedData, setSolvedData] = useState()
  const [tearData, setTearData] = useState()
  const accessToken = useRecoilValue(accessTokenState)
  const bojId = useRecoilValue(bojIdState)
  const algoId = useRecoilValue(algoIdState)

  const RecommendVul = async () => {
    await getRecoVul(accessToken, bojId)
      .then((res) => {
        const list = res.data.slice(0, 5)
        setVulData(list)
      })
  }

  const RecommendTag = async () => {
    await getRecoTag(accessToken, bojId)
      .then((res) => {
        const list = res.data.slice(0, 5)
        setTagData(list)
      })
  }

  const RecommendSolved = async () => {
    await getSolvedTear(accessToken, bojId)
      .then((res) => {
        const list = res.data.slice(0, 5)
        setSolvedData(list)
      })
  }

  const RecommendTear = async () => {
    await getRecoTear(accessToken, bojId)
      .then((res) => {
        const list = res.data.slice(0, 5)
        setTearData(list)
      })
  }

  useEffect(() => {
    RecommendVul()
    RecommendTag()
    RecommendSolved()
    RecommendTear()
  }, [])

  useEffect(() =>{
    setTimeout(() => {setAlert(false)},2000)
  }, [])

  const subjects = [
    {title: `# ${algoId}님이 많이 푼 문제 유형 🧐`, englishTitle: '', list: tagData},
    {title: `# ${algoId}님이 적게 푼 문제 유형 🧐`, englishTitle: '', list: vulData},
    {title: `# ${algoId}님이 풀었던 문제 🧐`, englishTitle: '', list: solvedData},
    {title: `# 다른 유저가 많이 푼 문제 유형 🧐`, englishTitle: '', list: tearData}
  ]

  return (
    <>
      <NextSeo
				title="마이페이지"
				description="여러분의 풀이를 기반으로 문제를 추천해줘요"
				openGraph={{
					type: 'website',
					url: 'https://www.algopulza.day/recommendation',
					title: '막 풀지 말고, 알고 풀자!',
					description: '여러분의 풀이를 기반으로 문제를 추천해줘요',
					images: [
						{
							url: 'https://www.algopulza.day/common/brand_logo.png',
              alt: '로고 사진'
						},
					],
          site_name: "알고 풀자",
				}}
			/>
      {alert?
        <CircularProgressContainer>
          <SubContainer>
            <CircularProgress sx={{ display: 'flex', justifyContent: 'center', marginBottom:"1rem" , color: '#282828' }}/>
            <div>사용자에게 맞는 추천 문제를 뽑아오고 있어요!</div>
          </SubContainer>
          <ButtonFloating />
        </CircularProgressContainer> :
        <>
          <Gift />
          <Container>
            {subjects.map((subject) => <Subject key={subject.title} subjectAttr={subject} />)}
          </Container>
          <ButtonFloating />
        </>
      }
    </>
  )
}

Recommendation.getLayout = function getLayout(recommendation: ReactElement) {
  return <Layout>{recommendation}</Layout>
}
