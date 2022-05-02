import { useState, useEffect } from "react";
import styled from "styled-components";
import { useLocation, useNavigate } from "react-router-dom";
import NavItem from "components/layout/NavItem";

const Container = styled.div`
  display: grid;
  grid-template-columns: 1fr 2fr 1fr;
  border-bottom: 1px solid black;
  padding: 1rem 5rem;
`;

const Title = styled.div`
  font-size: 2rem;
  font-family: "GmarketSansBold";
  cursor: pointer;
`;

const TabContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
  position: relative;
  align-items: center;
`;

const Button = styled.div`
  margin-left: auto;
  margin-right: 1rem;
  text-align: center;
  line-height: 3rem;
  width: 10rem;
  height: 3rem;
  font-size: 1rem;
  font-weight: 600;
  font-family: GmarketSansMedium;
  background-color: #ffc94d;
  color: white;
  border: none;
  border-radius: 10px;
  transition: 0.2s;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  cursor: pointer;
`

const Navbar = () => {
  const location = useLocation();
  const [isSelected, setIsSelected] = useState("/");
  const navigate = useNavigate();

  const onClick = (url: string) => {
    navigate(url);
    setIsSelected(url);
  };

  const onReset = () => {
    window.location.reload();
  }

  useEffect(() => {
    setIsSelected("/" + location.pathname.split("/")[1]);
  }, [location]);


  return (
    <Container>
      <Title onClick={() => navigate("/")}>알고 풀자</Title>
      <TabContainer>
        <NavItem
          url="/recommend"
          name="추천"
          onClick={onClick}
          isSelected={isSelected}
        />
        <NavItem
          url="/random"
          name="랜덤"
          onClick={onClick}
          isSelected={isSelected}
        />
        <NavItem
          url="/category"
          name="카테고리"
          onClick={onClick}
          isSelected={isSelected}
        />
        <NavItem
          url="/analysis"
          name="분석"
          onClick={onClick}
          isSelected={isSelected}
        />
      </TabContainer>
      <Button
      onClick={onReset}
      title="문제를 풀고 와서 업데이트를 하고 싶으면
      이 버튼을 눌러주세요!">
        문제 풀이 업데이트
        </Button>
    </Container>
  );
};

export default Navbar;