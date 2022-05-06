import styled from "styled-components";
import { Alert } from "../../common/alert/Alert";
import Seed from "../../../public/analysis/badge/seed.png";
import Image from 'next/image'

const Container = styled.div`
  width: 90%;
  height: 90%;
  border-radius: 10px;
  box-shadow: 0px 4px 4px 0 rgba(0, 0, 0, 0.25);
  display: flex;
  justify-content: center;
  align-items: center;
  /* grid-template-columns: 1fr 2fr; */
  padding: 1rem;
`;

const ProfileImage = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 3rem;
`;

const RightContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const NickName = styled.div`
  display: flex;
  flex-direction: row;
`;

const Tier = styled.div`
  border-radius: 10px;
  height: 4rem;
  width: 3rem;
  color: white;
  background-color: #27e2a4;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ConfirmNickName = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  font-size: 2rem;
  margin-left: 1rem;
`;

const Email = styled.div`
  margin-top: 1rem;
`;

export default function userinfo() {
  return (
    <Container>
    <ProfileImage>
      <Image src={Seed} alt="이미지를 찾을 수 없습니다." />
    </ProfileImage>
    <RightContainer>
      <NickName>
        <Tier>5</Tier>
        <ConfirmNickName>runkey</ConfirmNickName>
      </NickName>

      <Email>runkey@gmail.com</Email>
    </RightContainer>
  </Container>
  )
}