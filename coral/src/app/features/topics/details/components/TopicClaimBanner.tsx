import { Alert, Banner, Box, Button, Spacing } from "@aivenio/aquarium";
import { Dispatch, SetStateAction } from "react";
import { InternalLinkButton } from "src/app/components/InternalLinkButton";
import illustration from "src/app/images/topic-details-banner-Illustration.svg";

interface TopicClaimBannerProps {
  topicName: string;
  hasOpenRequest: boolean;
  hasOpenClaimRequest: boolean;
  setShowClaimModal: Dispatch<SetStateAction<boolean>>;
  isError: boolean;
  errorMessage?: string;
  topicOwner: string;
}

const TopicClaimBanner = ({
  topicName,
  hasOpenClaimRequest,
  hasOpenRequest,
  setShowClaimModal,
  isError,
  errorMessage,
  topicOwner,
}: TopicClaimBannerProps) => {
  if (hasOpenRequest) {
    // We do not render an InternalLinkButton to the Requests page for this state...
    // .. because a user cannot see the requests opened by members of other teams
    return (
      <Banner image={illustration} layout="vertical" title={""}>
        <Box.Flex minHeight={"full"}>
          <Box.Flex component={"p"} alignSelf={"center"}>
            {topicName} has pending requests. Your team cannot claim ownership
            at this time.
          </Box.Flex>
        </Box.Flex>
      </Banner>
    );
  }

  if (hasOpenClaimRequest) {
    return (
      <Banner image={illustration} layout="vertical" title={""}>
        <Box component={"p"} marginBottom={"l1"}>
          A claim request for {topicName} is already in progress.
        </Box>
        <InternalLinkButton
          to={`/requests/topics?search=${topicName}&requestType=CLAIM&status=CREATED&page=1`}
        >
          View request
        </InternalLinkButton>
      </Banner>
    );
  }

  return (
    <Banner image={illustration} layout="vertical" title={""}>
      <Spacing gap={"l1"}>
        {isError && <Alert type="error">{errorMessage}</Alert>}
        <Box component={"p"} marginBottom={"l1"}>
          This topic is currently owned by {topicOwner}. Select &quot;Claim
          topic&quot; to request ownership.
        </Box>
      </Spacing>
      <Button.Primary onClick={() => setShowClaimModal(true)}>
        Claim topic
      </Button.Primary>
    </Banner>
  );
};

export default TopicClaimBanner;
