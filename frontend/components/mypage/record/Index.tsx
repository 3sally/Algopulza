import React from "react";
import styled from "styled-components";
import Favorite from "./Favorite";
import Solved from "./Solved";

const Container = styled.div`
  display: grid;
  grid-template-columns: 1fr 3fr;
`;

const Index = () => {
  return (
    <Container>
      <Favorite />
      <Solved />
    </Container>
  );
};

export default Index;
